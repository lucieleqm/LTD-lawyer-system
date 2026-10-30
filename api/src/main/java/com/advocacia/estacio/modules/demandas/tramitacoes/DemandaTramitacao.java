package com.advocacia.estacio.modules.demandas.tramitacoes;

import com.advocacia.estacio.modules.demandas.Demanda;
import com.advocacia.estacio.modules.pessoas.Pessoa;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "tbl_demanda_tramitacao")
public class DemandaTramitacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "demanda_id", nullable = false)
    private Demanda demanda;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "responsavel_id", nullable = false)
    private Pessoa responsavel;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_tramitacao", nullable = false, length = 50)
    private TipoTramitacao tipoTramitacao;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "link_anexo")
    private String linkAnexo;

    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    protected DemandaTramitacao() {
    }

    //TODO: hashCode() e equals()

    @Builder
    public DemandaTramitacao(String observacoes, TipoTramitacao tipoTramitacao, Pessoa responsavel, String linkAnexo) {
        this.observacoes = observacoes;
        this.tipoTramitacao = tipoTramitacao;
        this.responsavel = responsavel;
        this.linkAnexo = linkAnexo;
    }
}