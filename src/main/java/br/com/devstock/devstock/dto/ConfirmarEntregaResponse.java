package br.com.devstock.devstock.dto;

import br.com.devstock.devstock.enums.StatusOnboard;

import java.time.LocalDateTime;

public record ConfirmarEntregaResponse (
        Long id,
        StatusOnboard status,
        LocalDateTime dataEntrega,
        String respinsavelEntrega
){}
