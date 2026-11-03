
package br.com.devstock.devstock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "onboarding_item")
public class OnboardingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "onboarding_id", nullable = false)
    @JsonIgnore
    private Onboarding onboarding;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notebook_id")
    @JsonIgnore
    private Notebook notebook;

    @Column(name = "tipo_item", nullable = false, length = 50)
    private String tipoItem;

    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    @Column(name = "identificador", length = 100)
    private String identificador;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "observacao_entrega", length = 500)
    private String observacaoEntrega;

    @Column(name = "observacao_devolucao", length = 500)
    private String observacaoDevolucao;

    // Getters e Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Onboarding getOnboarding() {
        return onboarding;
    }

    public void setOnboarding(Onboarding onboarding) {
        this.onboarding = onboarding;
    }

    public Notebook getNotebook() {
        return notebook;
    }

    public void setNotebook(Notebook notebook) {
        this.notebook = notebook;
    }

    public String getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(String tipoItem) {
        this.tipoItem = tipoItem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getObservacaoEntrega() {
        return observacaoEntrega;
    }

    public void setObservacaoEntrega(String observacaoEntrega) {
        this.observacaoEntrega = observacaoEntrega;
    }

    public String getObservacaoDevolucao() {
        return observacaoDevolucao;
    }

    public void setObservacaoDevolucao(String observacaoDevolucao) {
        this.observacaoDevolucao = observacaoDevolucao;
    }
}
