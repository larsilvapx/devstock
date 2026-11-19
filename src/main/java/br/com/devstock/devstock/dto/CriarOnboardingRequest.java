
package br.com.devstock.devstock.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CriarOnboardingRequest {

    @NotNull(message = "O ID do desenvolvedor deve ser informado.")
    @Positive(message = "O ID do desenvolvedor deve ser positivo.")
    private Long desenvolvedorId;

    @NotNull(message = "O ID do kit deve ser informado.")
    @Positive(message = "O ID do kit deve ser positivo.")
    private Long kitId;

    public Long getDesenvolvedorId() {
        return desenvolvedorId;
    }

    public void setDesenvolvedorId(Long desenvolvedorId) {
        this.desenvolvedorId = desenvolvedorId;
    }

    public Long getKitId() {
        return kitId;
    }

    public void setKitId(Long kitId) {
        this.kitId = kitId;
    }
}
