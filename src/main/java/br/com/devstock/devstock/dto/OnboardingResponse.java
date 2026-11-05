
package br.com.devstock.devstock.dto;

import br.com.devstock.devstock.enums.StatusOnboard;

import java.time.LocalDateTime;

public record OnboardingResponse(
        Long id,
        Long desenvolvedorId,
        Long kitId,
        StatusOnboard status,
        LocalDateTime dataCriacao
) {
}
