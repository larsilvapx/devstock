
package br.com.devstock.devstock.dto;

public class CriarOnboardingRequest {

    private Long desenvolvedorId;
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

