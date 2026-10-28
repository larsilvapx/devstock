package br.com.devstock.devstock.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "kit")
public class Kit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "observacao")
    private String observacao;

    @OneToMany(
            mappedBy = "kit",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<KitItem> itens = new ArrayList<>();

    public Kit(){

    }
    protected void prePersist(){
        if(dataCriacao == null){
            dataCriacao = LocalDateTime.now();
        }
    }

    public void adicionarItem(KitItem item){
        itens.add(item);
        item.setKit(this);
    }

    public void removerItem(KitItem item){
        itens.remove(item);
        item.setKit(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public List<KitItem> getItens() {
        return itens;
    }

    public void setItens(List<KitItem> itens) {
        this.itens.clear();

        if(itens != null){
            itens.forEach(this::adicionarItem);
        }
    }
}
