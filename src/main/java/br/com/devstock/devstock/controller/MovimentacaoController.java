
package br.com.devstock.devstock.controller;

import br.com.devstock.devstock.dto.MovimentacaoResponse;
import br.com.devstock.devstock.dto.RegistrarOcorrenciaRequest;
import br.com.devstock.devstock.dto.RegistrarOcorrenciaResponse;
import br.com.devstock.devstock.service.MovimentacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notebooks/{notebookId}")
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    public MovimentacaoController(
            MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    @PostMapping("/ocorrencias")
    public ResponseEntity<RegistrarOcorrenciaResponse> registrarOcorrencia(
            @PathVariable Long notebookId,
          @Valid @RequestBody RegistrarOcorrenciaRequest request) {

        RegistrarOcorrenciaResponse resposta =
                movimentacaoService.registrarOcorrencia(
                        notebookId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resposta);
    }

    @GetMapping("/movimentacoes")
    public ResponseEntity<List<MovimentacaoResponse>> listarHistorico(
            @PathVariable Long notebookId) {

        List<MovimentacaoResponse> historico =
                movimentacaoService.listarHistoricoPorNotebook(notebookId);

        return ResponseEntity.ok(historico);
    }
}
