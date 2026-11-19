
package br.com.devstock.devstock.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ConfirmarEntregaRequest {

    @NotBlank(message = "O responsável pela entrega deve ser informado.")
    @Size(max = 150, message = "O responsável deve possuir no máximo 150 caracteres.")
    private String responsavel;

    @Size(max = 500, message = "A observação deve possuir no máximo 500 caracteres.")
    private String observacao;

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
