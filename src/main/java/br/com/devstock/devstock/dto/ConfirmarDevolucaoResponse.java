package br.com.devstock.devstock.dto;

import br.com.devstock.devstock.enums.StatusOnboard;

import java.time.LocalDateTime;

public record ConfirmarDevolucaoResponse(
        Long id,
        StatusOnboard status,
        LocalDateTime dataDevolucao,
        String responseDevolucao
) {
}
