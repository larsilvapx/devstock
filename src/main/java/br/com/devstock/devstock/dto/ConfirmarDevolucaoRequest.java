
package br.com.devstock.devstock.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ConfirmarDevolucaoRequest(

        @NotBlank(message = "O responsável pela devolução deve ser informado.")
        @Size(max = 150, message = "O responsável deve possuir no máximo 150 caracteres.")
        String responsavel,

        @Size(max = 500, message = "A observação deve possuir no máximo 500 caracteres.")
        String observacao,

        @NotEmpty(message = "Informe pelo menos um item para devolução.")
        List<@Valid DevolucaoItemRequest> itens

) {}
