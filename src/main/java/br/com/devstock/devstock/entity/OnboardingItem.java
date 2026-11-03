
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
}
