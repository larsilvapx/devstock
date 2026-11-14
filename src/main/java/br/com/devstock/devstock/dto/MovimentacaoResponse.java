package br.com.devstock.devstock.dto;

import br.com.devstock.devstock.enums.TipoMovimentacao;

import java.time.LocalDateTime;

public record MovimentacaoResponse(
        Long id,
        Long notebookId,
        Long onboardId,
        TipoMovimentacao tipo,
        LocalDateTime dataHora,
        String responsavel,
        String observacao
) {
}
