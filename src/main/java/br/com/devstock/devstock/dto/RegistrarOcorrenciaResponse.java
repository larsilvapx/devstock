package br.com.devstock.devstock.dto;

import br.com.devstock.devstock.enums.TipoMovimentacao;

import java.time.LocalDateTime;

public record RegistrarOcorrenciaResponse(
        Long id,
        Long notebookId,
        TipoMovimentacao tipo,
        LocalDateTime dataHora,
        String responsavel,
        String observacao
){

}
