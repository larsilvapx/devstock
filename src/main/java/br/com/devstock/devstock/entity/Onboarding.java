package br.com.devstock.devstock.entity;

import br.com.devstock.devstock.enums.StatusOnboard;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "onboarding")
public class Onboarding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "desenvolvedor_id", nullable = false)
    private Desenvolvedor desenvolvedor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kit_id", nullable = false)
    private Kit kit;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusOnboard status;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_entrega")
    private LocalDateTime dataEntrega;

    @Column(name = "responsavel_entrega", length = 150)
    private String responsavelEntrega;

    @Column(name = "data_devolucao")
    private LocalDateTime dataDevolucao;

    @Column(name = "responsavel_devolucao", length = 150)
    private String responsavelDevolucao;

    @Column(name = "observacao_devolucao", length = 500)
    private String observacaoDevolucao;

    @PrePersist
    protected void prePersist(){
        if (dataCriacao == null){
            dataCriacao = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Desenvolvedor getDesenvolvedor() {
        return desenvolvedor;
    }

    public void setDesenvolvedor(Desenvolvedor desenvolvedor) {
        this.desenvolvedor = desenvolvedor;
    }

    public Kit getKit() {
        return kit;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public StatusOnboard getStatus() {
        return status;
    }

    public void setStatus(StatusOnboard status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDateTime dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getResponsavelEntrega() {
        return responsavelEntrega;
    }

    public void setResponsavelEntrega(String responsavelEntrega) {
        this.responsavelEntrega = responsavelEntrega;
    }

    public LocalDateTime getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDateTime dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public String getResponsavelDevolucao() {
        return responsavelDevolucao;
    }

    public void setResponsavelDevolucao(String responsavelDevolucao) {
        this.responsavelDevolucao = responsavelDevolucao;
    }

    public String getObservacaoDevolucao() {
        return observacaoDevolucao;
    }

    public void setObservacaoDevolucao(String observacaoDevolucao) {
        this.observacaoDevolucao = observacaoDevolucao;
    }

}
