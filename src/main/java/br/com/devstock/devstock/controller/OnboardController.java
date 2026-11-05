
package br.com.devstock.devstock.controller;

import br.com.devstock.devstock.dto.CriarOnboardingRequest;
import br.com.devstock.devstock.dto.OnboardingResponse;
import br.com.devstock.devstock.entity.Onboarding;
import br.com.devstock.devstock.service.OnboardService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
}
