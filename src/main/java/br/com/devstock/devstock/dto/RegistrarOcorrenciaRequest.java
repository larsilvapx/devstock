
package br.com.devstock.devstock.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrarOcorrenciaRequest(

        @NotBlank(message = "O responsável pela ocorrência deve ser informado.")
        @Size(max = 150, message = "O responsável deve possuir no máximo 150 caracteres.")
        String responsavel,

        @NotBlank(message = "A descrição da ocorrência deve ser informada.")
        @Size(max = 500, message = "A descrição da ocorrência deve possuir no máximo 500 caracteres.")
        String observacao

) {}
