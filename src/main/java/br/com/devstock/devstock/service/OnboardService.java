
package br.com.devstock.devstock.service;

import br.com.devstock.devstock.entity.*;
import br.com.devstock.devstock.enums.StatusOnboard;
import br.com.devstock.devstock.exception.ConflitoCadastroException;
import br.com.devstock.devstock.exception.RecursoNaoEncontradoException;
import br.com.devstock.devstock.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OnboardService {

    private static final String TIPO_NOTEBOOK = "NOTEBOOK";
    private static final String STATUS_DISPONIVEL = "DISPONIVEL";

    private final OnboardRepository onboardRepository;
    private final OnboardItemRepository onboardItemRepository;
    private final DesenvolvedorRepository desenvolvedorRepository;
    private final KitRepository kitRepository;
    private final NotebookRepository notebookRepository;

    public OnboardService(
            OnboardRepository onboardRepository,
            OnboardItemRepository onboardItemRepository,
            DesenvolvedorRepository desenvolvedorRepository,
            KitRepository kitRepository,
            NotebookRepository notebookRepository
    ) {
        this.onboardRepository = onboardRepository;
        this.onboardItemRepository = onboardItemRepository;
        this.desenvolvedorRepository = desenvolvedorRepository;
        this.kitRepository = kitRepository;
        this.notebookRepository = notebookRepository;
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
}
