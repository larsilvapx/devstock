
package br.com.devstock.devstock.controller;

import br.com.devstock.devstock.entity.Notebook;
import br.com.devstock.devstock.service.NotebookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(
        name = "Notebooks",
        description = "Gerenciamento de notebooks e equipamentos"
)
@RestController
@RequestMapping("/api/notebooks")
public class NotebookController {

    private final NotebookService notebookService;

    public NotebookController(NotebookService notebookService) {
        this.notebookService = notebookService;
    }

    @Operation(
            summary = "Cadastrar notebook",
            description = "Cadastra um novo notebook no inventário do DevStock."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Notebook cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "409", description = "Conflito ao cadastrar notebook"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public ResponseEntity<Notebook> cadastrar(
            @RequestBody Notebook notebook) {

        Notebook notebookSalvo = notebookService.cadastrar(notebook);

        URI localizacao = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(notebookSalvo.getId())
                .toUri();

        return ResponseEntity
                .created(localizacao)
                .body(notebookSalvo);
    }

    @Operation(
            summary = "Listar notebooks",
            description = "Retorna todos os notebooks cadastrados no inventário."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de notebooks retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public List<Notebook> listarTodos() {
        return notebookService.listarTodos();
    }

    @Operation(
            summary = "Buscar notebook por ID",
            description = "Retorna os dados de um notebook a partir do seu identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notebook encontrado"),
            @ApiResponse(responseCode = "404", description = "Notebook não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{id}")
    public Notebook buscarPorId(@PathVariable Long id) {
        return notebookService.buscarPorId(id);
    }

    @Operation(
            summary = "Atualizar notebook",
            description = "Atualiza os dados de um notebook existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notebook atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Notebook não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito ao atualizar notebook"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/{id}")
    public Notebook atualizar(
            @PathVariable Long id,
            @RequestBody Notebook notebook) {
        return notebookService.atualizar(id, notebook);
    }

    @Operation(
            summary = "Excluir notebook",
            description = "Remove um notebook do inventário pelo seu identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Notebook excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Notebook não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        notebookService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
