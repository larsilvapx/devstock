package br.com.devstock.devstock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import br.com.devstock.devstock.dto.*;
import br.com.devstock.devstock.entity.Onboarding;
import br.com.devstock.devstock.service.OnboardService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(
        name = "Onboardings",
        description = "Gerenciamento de onboardings, entregas e devoluções de equipamentos"
)
@RestController
@RequestMapping("/api/onboardings")
public class OnboardController {

    private final OnboardService onboardService;

    public OnboardController(OnboardService onboardService) {
        this.onboardService = onboardService;
    }

    @Operation(
            summary = "Criar onboarding",
            description = "Cria um onboarding para um desenvolvedor utilizando um kit de equipamentos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Onboarding criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Desenvolvedor ou kit não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito na criação do onboarding"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public ResponseEntity<OnboardingResponse> criar(
            @Valid @RequestBody CriarOnboardingRequest request
    ) {

        Onboarding onboarding = onboardService.criarOnboarding(
                request.getDesenvolvedorId(),
                request.getKitId()
        );

        OnboardingResponse response = new OnboardingResponse(
                onboarding.getId(),
                onboarding.getDesenvolvedor().getId(),
                onboarding.getKit().getId(),
                onboarding.getStatus(),
                onboarding.getDataCriacao()
        );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(onboarding.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(response);
    }

    @Operation(
            summary = "Confirmar entrega de equipamentos",
            description = "Confirma a entrega dos equipamentos do onboarding e registra a movimentação dos notebooks."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entrega confirmada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou estado incompatível"),
            @ApiResponse(responseCode = "404", description = "Onboarding não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito na confirmação da entrega"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/{id}/confirmar-entrega")
    public ResponseEntity<ConfirmarEntregaResponse> confirmarEntrega(
            @PathVariable Long id,
            @Valid @RequestBody ConfirmarEntregaRequest request
    ) {

        Onboarding onboarding = onboardService.confirmarEntrega(
                id,
                request.getResponsavel(),
                request.getObservacao()
        );

        ConfirmarEntregaResponse response = new ConfirmarEntregaResponse(
                onboarding.getId(),
                onboarding.getStatus(),
                onboarding.getDataEntrega(),
                onboarding.getResponsavelEntrega()
        );

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Confirmar devolução de equipamentos",
            description = "Registra a devolução dos equipamentos e suas respectivas condições."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Devolução confirmada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Onboarding ou item não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito na confirmação da devolução"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/{id}/confirmar-devolucao")
    public ResponseEntity<ConfirmarDevolucaoResponse> confirmarDevolucao(
            @PathVariable Long id,
            @Valid @RequestBody ConfirmarDevolucaoRequest request
    ) {

        Onboarding onboarding = onboardService.confirmarDevolucao(
                id,
                request
        );

        ConfirmarDevolucaoResponse response = new ConfirmarDevolucaoResponse(
                onboarding.getId(),
                onboarding.getStatus(),
                onboarding.getDataDevolucao(),
                onboarding.getResponsavelDevolucao()
        );

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Listar onboardings",
            description = "Retorna a lista de onboardings cadastrados."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<List<OnboardingResponse>> listar() {

        List<OnboardingResponse> response = onboardService
                .listarOnboardings()
                .stream()
                .map(onboarding -> new OnboardingResponse(
                        onboarding.getId(),
                        onboarding.getDesenvolvedor().getId(),
                        onboarding.getKit().getId(),
                        onboarding.getStatus(),
                        onboarding.getDataCriacao()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Buscar onboarding por ID",
            description = "Retorna os dados de um onboarding específico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Onboarding encontrado"),
            @ApiResponse(responseCode = "404", description = "Onboarding não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OnboardingResponse> buscarPorId(
            @PathVariable Long id
    ) {

        Onboarding onboarding = onboardService.buscarOnboarding(id);

        OnboardingResponse response = new OnboardingResponse(
                onboarding.getId(),
                onboarding.getDesenvolvedor().getId(),
                onboarding.getKit().getId(),
                onboarding.getStatus(),
                onboarding.getDataCriacao()
        );

        return ResponseEntity.ok(response);
    }
}