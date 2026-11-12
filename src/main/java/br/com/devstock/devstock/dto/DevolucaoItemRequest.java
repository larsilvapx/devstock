package br.com.devstock.devstock.dto;

import br.com.devstock.devstock.enums.SituacaoDevolucao;

public record DevolucaoItemRequest(

        Long onboardingItemId,
        SituacaoDevolucao situacao,
        String observacao
) {
}
