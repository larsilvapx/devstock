
package br.com.devstock.devstock.controller;

import br.com.devstock.devstock.dto.MovimentacaoResponse;
import br.com.devstock.devstock.dto.RegistrarOcorrenciaRequest;
import br.com.devstock.devstock.dto.RegistrarOcorrenciaResponse;
import br.com.devstock.devstock.service.MovimentacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notebooks/{notebookId}")
@Tag(
        name = "Movimentações",
        description = "Registro de ocorrências e consulta do histórico de movimentações dos notebooks"
)
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    public MovimentacaoController(
            MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    @PostMapping("/ocorrencias")
    @Operation(
            summary = "Registrar ocorrência de notebook",
            description = "Registra uma ocorrência vinculada a um notebook, como avaria, problema ou observação."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ocorrência registrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da ocorrência inválidos"),
            @ApiResponse(responseCode = "404", description = "Notebook não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito ao registrar ocorrência"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
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
    @Operation(
            summary = "Consultar histórico de movimentações",
            description = "Retorna o histórico de movimentações de um notebook específico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Histórico retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Notebook não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<MovimentacaoResponse>> listarHistorico(
            @PathVariable Long notebookId) {

        List<MovimentacaoResponse> historico =
                movimentacaoService.listarHistoricoPorNotebook(notebookId);

        return ResponseEntity.ok(historico);
    }
}
