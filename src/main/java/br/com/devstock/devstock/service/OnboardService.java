
package br.com.devstock.devstock.service;

import br.com.devstock.devstock.entity.*;
import br.com.devstock.devstock.enums.StatusOnboard;
import br.com.devstock.devstock.enums.TipoMovimentacao;
import br.com.devstock.devstock.exception.ConflitoCadastroException;
import br.com.devstock.devstock.exception.RecursoNaoEncontradoException;
import br.com.devstock.devstock.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OnboardService {

    private static final String TIPO_NOTEBOOK = "NOTEBOOK";
    private static final String STATUS_DISPONIVEL = "DISPONIVEL";
    private static final String STATUS_EM_USO = "EM_USO";

    private final OnboardRepository onboardRepository;
    private final OnboardItemRepository onboardItemRepository;
    private final DesenvolvedorRepository desenvolvedorRepository;
    private final KitRepository kitRepository;
    private final NotebookRepository notebookRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    public OnboardService(
            OnboardRepository onboardRepository,
            OnboardItemRepository onboardItemRepository,
            DesenvolvedorRepository desenvolvedorRepository,
            KitRepository kitRepository,
            NotebookRepository notebookRepository,
            MovimentacaoRepository movimentacaoRepository
    ) {
        this.onboardRepository = onboardRepository;
        this.onboardItemRepository = onboardItemRepository;
        this.desenvolvedorRepository = desenvolvedorRepository;
        this.kitRepository = kitRepository;
        this.notebookRepository = notebookRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    @Transactional
    public Onboarding criarOnboarding(Long desenvolvedorId, Long kitId) {

        // 1. Localizar o desenvolvedor
        Desenvolvedor desenvolvedor = desenvolvedorRepository
                .findById(desenvolvedorId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Desenvolvedor não encontrado: " + desenvolvedorId
                ));

        // 2. Localizar o kit
        Kit kit = kitRepository
                .findById(kitId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Kit não encontrado: " + kitId
                ));

        // 3. Verificar se já existe onboarding em andamento
        boolean possuiOnboardingAtivo =
                onboardRepository.existsByDesenvolvedorIdAndStatus(
                        desenvolvedorId,
                        StatusOnboard.EM_ANDAMENTO
                );

        if (possuiOnboardingAtivo) {
            throw new ConflitoCadastroException(
                    "O desenvolvedor já possui um onboarding em andamento."
            );
        }

        // 4. Verificar se o kit possui itens
        if (kit.getItens() == null || kit.getItens().isEmpty()) {
            throw new ConflitoCadastroException(
                    "Não é possível criar onboarding com um kit sem itens."
            );
        }

        // 5. Criar o onboarding
        Onboarding onboarding = new Onboarding();

        onboarding.setDesenvolvedor(desenvolvedor);
        onboarding.setKit(kit);
        onboarding.setStatus(StatusOnboard.EM_ANDAMENTO);

        onboarding = onboardRepository.save(onboarding);

        // 6. Preparar a cópia dos itens do kit
        List<OnboardingItem> itensOnboarding = new ArrayList<>();

        for (KitItem item : kit.getItens()) {

            OnboardingItem onboardingItem = new OnboardingItem();

            onboardingItem.setOnboarding(onboarding);
            onboardingItem.setTipoItem(item.getTipoItem());
            onboardingItem.setDescricao(item.getDescricao());
            onboardingItem.setIdentificador(item.getIdentificador());
            onboardingItem.setQuantidade(item.getQuantidade());

            // 7. Se for notebook, localizar o equipamento pelo serial
            if (TIPO_NOTEBOOK.equalsIgnoreCase(
                    item.getTipoItem() == null ? "" : item.getTipoItem().trim()
            )) {

                if (item.getIdentificador() == null ||
                        item.getIdentificador().isBlank()) {
                    throw new ConflitoCadastroException(
                            "O item NOTEBOOK do kit precisa possuir um número de série."
                    );
                }

                Notebook notebook = notebookRepository
                        .findBySerialNumber(item.getIdentificador().trim())
                        .orElseThrow(() -> new RecursoNaoEncontradoException(
                                "Notebook não encontrado com número de série: "
                                        + item.getIdentificador()
                        ));

                if (!STATUS_DISPONIVEL.equalsIgnoreCase(notebook.getStatus())) {
                    throw new ConflitoCadastroException(
                            "O notebook " + notebook.getSerialNumber()
                                    + " não está disponível. Status atual: "
                                    + notebook.getStatus()
                    );
                }

                onboardingItem.setNotebook(notebook);
            }

            itensOnboarding.add(onboardingItem);
        }

        // 8. Salvar todos os itens do onboarding
        onboardItemRepository.saveAll(itensOnboarding);

        return onboarding;
    }

    @Transactional
    public Onboarding confirmarEntrega(
            Long onboardingId,
            String responsavel,
            String observacao
    ) {

        // 1. Validar o responsável pela entrega
        if (responsavel == null || responsavel.isBlank()) {
            throw new ConflitoCadastroException(
                    "O responsável pela entrega deve ser informado."
            );
        }

        // 2. Localizar o onboarding
        Onboarding onboarding = onboardRepository
                .findById(onboardingId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Onboarding não encontrado: " + onboardingId
                ));

        // 3. Verificar se o onboarding está em andamento
        if (onboarding.getStatus() != StatusOnboard.EM_ANDAMENTO) {
            throw new ConflitoCadastroException(
                    "Somente onboardings em andamento podem receber uma entrega."
            );
        }

        // 4. Impedir confirmação duplicada
        if (onboarding.getDataEntrega() != null) {
            throw new ConflitoCadastroException(
                    "A entrega deste onboarding já foi confirmada."
            );
        }

        // 5. Localizar os itens do onboarding
        List<OnboardingItem> itens =
                onboardItemRepository.findByOnboardingId(onboardingId);

        // 6. Processar os notebooks associados
        for (OnboardingItem item : itens) {

            Notebook notebook = item.getNotebook();

            // Ignorar acessórios e itens que não são notebooks
            if (notebook == null) {
                continue;
            }

            // Verificar disponibilidade do equipamento
            if (!STATUS_DISPONIVEL.equalsIgnoreCase(notebook.getStatus())) {
                throw new ConflitoCadastroException(
                        "O notebook " + notebook.getSerialNumber()
                                + " não está disponível para entrega."
                );
            }

            // Atualizar o status do notebook
            notebook.setStatus(STATUS_EM_USO);
            notebookRepository.save(notebook);

            // Criar registro da movimentação
            Movimentacao movimentacao = new Movimentacao();

            movimentacao.setNotebook(notebook);
            movimentacao.setOnboarding(onboarding);
            movimentacao.setTipoMovimentacao(TipoMovimentacao.ENTREGA);
            movimentacao.setResponsavel(responsavel.trim());

            if (observacao != null && !observacao.isBlank()) {
                movimentacao.setObservacao(observacao.trim());
            }

            movimentacaoRepository.save(movimentacao);
        }

        // 7. Registrar os dados da entrega no onboarding
        onboarding.setDataEntrega(LocalDateTime.now());
        onboarding.setResponsavelEntrega(responsavel.trim());

        return onboardRepository.save(onboarding);
    }

    @Transactional(readOnly = true)
    public List<Onboarding> buscarOnboarding(){
        return onboardRepository.findAll();
    }

    @Transactional
    public Onboarding buscarOnboarding(Long id){
        return onboardRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Onboard não encontrado." + id
                ));
    }

    @Transactional(readOnly = true)
    public List<Onboarding> listarOnboardings() {
        return onboardRepository.findAll();
    }
}
