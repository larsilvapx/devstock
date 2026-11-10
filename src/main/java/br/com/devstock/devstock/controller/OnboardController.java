
package br.com.devstock.devstock.controller;

import br.com.devstock.devstock.dto.ConfirmarEntregaRequest;
import br.com.devstock.devstock.dto.ConfirmarEntregaResponse;
import br.com.devstock.devstock.dto.CriarOnboardingRequest;
import br.com.devstock.devstock.dto.OnboardingResponse;
import br.com.devstock.devstock.entity.Onboarding;
import br.com.devstock.devstock.service.OnboardService;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/onboardings")
public class OnboardController {

    private final OnboardService onboardService;

    public OnboardController(OnboardService onboardService) {
        this.onboardService = onboardService;
    }

    @PostMapping
    public ResponseEntity<OnboardingResponse> criar(
            @RequestBody CriarOnboardingRequest request
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

    @PutMapping("/{id}/confirmar-entrega")
    public ResponseEntity<ConfirmarEntregaResponse> confirmarEntrega(
            @PathVariable Long id,
            @RequestBody ConfirmarEntregaRequest request
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
