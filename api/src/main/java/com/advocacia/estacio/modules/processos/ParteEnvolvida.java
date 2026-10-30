package com.advocacia.estacio.modules.processos;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_parte_envolvida")
@Getter
public class ParteEnvolvida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "processo_id", nullable = false)
    private Processo processo;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel", nullable = false, length = 50)
    private PapelNoProcesso papel;

    // preenchido caso papel seja 'OUTROS'
    @Column(name = "especificacao_papel", length = 50)
    private String especificacaoPapel;

    protected ParteEnvolvida() {
    }

    @Builder
    public ParteEnvolvida(Processo processo, String nome, PapelNoProcesso papel, String especificacaoPapel) {
        this.processo = processo;
        this.nome = nome;
        this.papel = papel;
        this.especificacaoPapel = especificacaoPapel;
    }

    // TODO: hashCode() e equals()
}