
package br.com.devstock.devstock.service;

import br.com.devstock.devstock.dto.MovimentacaoResponse;
import br.com.devstock.devstock.dto.RegistrarOcorrenciaRequest;
import br.com.devstock.devstock.dto.RegistrarOcorrenciaResponse;
import br.com.devstock.devstock.entity.Movimentacao;
import br.com.devstock.devstock.entity.Notebook;
import br.com.devstock.devstock.enums.TipoMovimentacao;
import br.com.devstock.devstock.exception.RecursoNaoEncontradoException;
import br.com.devstock.devstock.repository.MovimentacaoRepository;
import br.com.devstock.devstock.repository.NotebookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final NotebookRepository notebookRepository;

    public MovimentacaoService(
            MovimentacaoRepository movimentacaoRepository,
            NotebookRepository notebookRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.notebookRepository = notebookRepository;
    }

    @Transactional
    public RegistrarOcorrenciaResponse registrarOcorrencia(
            Long notebookId,
            RegistrarOcorrenciaRequest request) {

        // Valida os dados recebidos
        if (request == null
                || request.responsavel() == null
                || request.responsavel().isBlank()) {
            throw new IllegalArgumentException(
                    "O responsável pelo registro é obrigatório."
            );
        }

        if (request.observacao() == null
                || request.observacao().isBlank()) {
            throw new IllegalArgumentException(
                    "A descrição da ocorrência é obrigatória."
            );
        }

        // Busca o notebook
        Notebook notebook = notebookRepository.findById(notebookId)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Notebook não encontrado com ID: " + notebookId
                        )
                );

        // Cria a movimentação
        Movimentacao movimentacao = new Movimentacao();

        movimentacao.setNotebook(notebook);
        movimentacao.setOnboarding(null);
        movimentacao.setTipoMovimentacao(TipoMovimentacao.OCORRENCIA);
        movimentacao.setResponsavel(request.responsavel().trim());
        movimentacao.setObservacao(request.observacao().trim());

        // Persiste a ocorrência
        Movimentacao movimentacaoSalva =
                movimentacaoRepository.save(movimentacao);

        // Retorna o DTO de resposta
        return new RegistrarOcorrenciaResponse(
                movimentacaoSalva.getId(),
                notebook.getId(),
                movimentacaoSalva.getTipoMovimentacao(),
                movimentacaoSalva.getDataHora(),
                movimentacaoSalva.getResponsavel(),
                movimentacaoSalva.getObservacao()
        );
    }
    @Transactional
    public List<MovimentacaoResponse> listarHistoricoPorNotebook(
            Long notebookId){
        Notebook notebook = notebookRepository.findById(notebookId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Notebook não encontrado com ID "+ notebookId
                ));
        return movimentacaoRepository
                .findByNotebookOrderByDataHoraDesc(notebook)
                .stream()
                .map(movimentacao -> new MovimentacaoResponse(
                        movimentacao.getId(),
                        notebook.getId(),
                        movimentacao.getOnboarding() != null
                        ? movimentacao.getOnboarding().getId() : null,
                        movimentacao.getTipoMovimentacao(),
                        movimentacao.getDataHora(),
                        movimentacao.getResponsavel(),
                        movimentacao.getObservacao()
                ))
                .collect(Collectors.toList());
    }
}
