package com.advocacia.estacio.modules.atendimento.demandas;

import com.advocacia.estacio.modules.membros.advogados.Advogado;
import com.advocacia.estacio.modules.atendimento.demandas.tramitacoes.DemandaTramitacao;
import com.advocacia.estacio.modules.membros.estagiarios.Estagiario;
import com.advocacia.estacio.modules.membros.professores.Professor;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_demanda")
@Getter
public class Demanda implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advogado_id")
    private Advogado advogado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estagiario_id")
    private Estagiario estagiario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "prazo_final", nullable = false)
    private LocalDate prazoFinal;

    @Column(name = "prazo_documentos")
    private LocalDate prazoDocumentos;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "etapa_atual")
    private EtapaDemanda etapaAtual = EtapaDemanda.ELABORACAO;

    @OneToMany(mappedBy = "demanda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DemandaTramitacao> tramitacoes = new ArrayList<>();

    @Column(name = "data_abertura", updatable = false)
    @CreationTimestamp
    private LocalDateTime dataAbertura;

    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;

    protected Demanda() {
    }

    public void atualizarDados(Advogado advogado, Estagiario estagiario, Professor professor,
                               String descricao, LocalDate prazoDocumentos, Integer diasAdicionais,
                               EtapaDemanda etapaAtual) {

        this.advogado = advogado;
        this.estagiario = estagiario;
        this.professor = professor;
        this.descricao = descricao;
        this.prazoDocumentos = prazoDocumentos;

        if (this.prazoDocumentos != null && diasAdicionais != null) {
            this.prazoFinal = this.prazoDocumentos.plusDays(diasAdicionais);
        }

        this.etapaAtual = etapaAtual;

    }

    // TODO: hashCode() e equals()

    @Builder
    public Demanda(Advogado advogado, Estagiario estagiario, Professor professor,
                   String descricao, LocalDate prazoFinal, LocalDate prazoDocumentos,
                   EtapaDemanda etapaAtual, List<DemandaTramitacao> tramitacoes,
                   LocalDateTime dataAbertura, LocalDateTime ultimaAtualizacao) {

        this.advogado = advogado;
        this.estagiario = estagiario;
        this.professor = professor;
        this.descricao = descricao;
        this.prazoFinal = prazoFinal;
        this.prazoDocumentos = prazoDocumentos;
        this.dataAbertura = dataAbertura;
        this.ultimaAtualizacao = ultimaAtualizacao;
        this.etapaAtual = etapaAtual != null ? etapaAtual : EtapaDemanda.ELABORACAO;
        this.tramitacoes = tramitacoes != null ? tramitacoes : new ArrayList<>();
    }

    // ===============================================
    // MÉTODOS AUXILIARES
    // ===============================================

    /**
     * Adiciona uma tramitação à Demanda e sincroniza o estado de ambos.
     */
    public void adicionarTramitacao(DemandaTramitacao tramitacao) {
        this.tramitacoes.add(tramitacao);
        tramitacao.setDemanda(this);
        if (tramitacao.getTipoTramitacao().getEtapaDestino() != null) {
            this.etapaAtual = tramitacao.getTipoTramitacao().getEtapaDestino();
        }
        this.ultimaAtualizacao = LocalDateTime.now();
    }

    @Transient
    public Tempestividade getTempestividade() {
        return LocalDate.now(ZoneId.of("America/Fortaleza")).isAfter(prazoFinal)
                ? Tempestividade.FORA_DO_PRAZO
                : Tempestividade.DENTRO_DO_PRAZO;
    }

}
