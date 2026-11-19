
package br.com.devstock.devstock.controller;

import br.com.devstock.devstock.entity.Kit;
import br.com.devstock.devstock.service.KitService;
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
        name = "Kits",
        description = "Gerenciamento de kits de equipamentos"
)
@RestController
@RequestMapping("/api/kits")
public class KitController {

    private final KitService kitService;

    public KitController(KitService kitService) {
        this.kitService = kitService;
    }

    @Operation(
            summary = "Listar kits",
            description = "Retorna todos os kits cadastrados no DevStock."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de kits retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<List<Kit>> listarTodos() {
        return ResponseEntity.ok(kitService.listarTodos());
    }

    @Operation(
            summary = "Buscar kit por ID",
            description = "Retorna os dados de um kit a partir do seu identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Kit encontrado"),
            @ApiResponse(responseCode = "404", description = "Kit não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Kit> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(kitService.buscarPorId(id));
    }

    @Operation(
            summary = "Cadastrar kit",
            description = "Cadastra um novo kit de equipamentos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Kit cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "409", description = "Conflito ao cadastrar kit"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public ResponseEntity<Kit> cadastrar(@RequestBody Kit kit) {

        Kit kitCadastrado = kitService.cadastrar(kit);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(kitCadastrado.getId())
                .toUri();

        return ResponseEntity.created(location).body(kitCadastrado);
    }

    @Operation(
            summary = "Atualizar kit",
            description = "Atualiza os dados de um kit existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Kit atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Kit não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito ao atualizar kit"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Kit> atualizar(
            @PathVariable Long id,
            @RequestBody Kit kit
    ) {
        return ResponseEntity.ok(kitService.atualizar(id, kit));
    }
}
