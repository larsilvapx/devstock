package br.com.devstock.devstock.controller;

import br.com.devstock.devstock.entity.Desenvolvedor;
import br.com.devstock.devstock.service.DesenvolvedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/desenvolvedores")
@Tag(
        name = "Desenvolvedores",
        description = "Gerenciamento de desenvolvedores"
)
public class DesenvolvedorController {

    private final DesenvolvedorService desenvolvedorService;

    public DesenvolvedorController(DesenvolvedorService desenvolvedorService) {
        this.desenvolvedorService = desenvolvedorService;
    }

    @GetMapping
    @Operation(
            summary = "Listar desenvolvedores",
            description = "Retorna todos os desenvolvedores cadastrados."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public List<Desenvolvedor> listarTodos() {
        return desenvolvedorService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar desenvolvedor por ID",
            description = "Retorna os dados de um desenvolvedor a partir do seu identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Desenvolvedor encontrado"),
            @ApiResponse(responseCode = "404", description = "Desenvolvedor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Desenvolvedor buscarPorId(@PathVariable Long id) {
        return desenvolvedorService.buscarPorId(id);
    }

    @PostMapping
    @Operation(
            summary = "Cadastrar desenvolvedor",
            description = "Realiza o cadastro de um novo desenvolvedor."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Desenvolvedor cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "409", description = "Conflito de cadastro"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Desenvolvedor cadastrar(
            @RequestBody Desenvolvedor desenvolvedor) {
        return desenvolvedorService.cadastrar(desenvolvedor);
    }

}