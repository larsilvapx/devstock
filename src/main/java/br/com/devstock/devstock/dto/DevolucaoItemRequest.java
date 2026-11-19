
package br.com.devstock.devstock.dto;

import br.com.devstock.devstock.enums.SituacaoDevolucao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record DevolucaoItemRequest(

        @NotNull(message = "O ID do item do onboarding deve ser informado.")
        @Positive(message = "O ID do item do onboarding deve ser positivo.")
        Long onboardingItemId,

        @NotNull(message = "A situação da devolução deve ser informada.")
        SituacaoDevolucao situacao,

        @Size(max = 500, message = "A observação deve possuir no máximo 500 caracteres.")
        String observacao

) {}
