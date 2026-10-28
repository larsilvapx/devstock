package br.com.devstock.devstock.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "kit_item")
public class KitItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kit_id", nullable = false)
    @JsonIgnore
    private Kit kit;

    @Column(name = "tipo_item", nullable = false)
    private String tipoItem;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "identificador")
    private String identificador;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade = 1;

    @Column(name = "observacao")
    private String observacao;

    public KitItem() {
    }

    public Long getId() {
        return id;
    }

    public Kit getKit() {
        return kit;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
