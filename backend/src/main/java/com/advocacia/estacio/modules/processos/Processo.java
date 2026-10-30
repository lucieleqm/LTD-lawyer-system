package com.advocacia.estacio.modules.processos;

import com.advocacia.estacio.modules.advogados.Advogado;
import com.advocacia.estacio.modules.assistidos.Assistido;
import com.advocacia.estacio.modules.estagiarios.Estagiario;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_processo")
@Getter
public class Processo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_processo", unique = true, nullable = false, length = 50)
    private String numeroProcesso;

    @Column(name = "numero_processo_pje", unique = true, length = 50)
    private String numeroProcessoPje;

    @Column(name = "area_direito")
    @Enumerated(EnumType.STRING)
    private AreaDireito areaDireito;

    @Enumerated(EnumType.STRING)
    private Tribunal tribunal;

    private String assunto;

    @Column(length = 100)
    private String vara;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advogado_id")
    private Advogado advogado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estagiario_id")
    private Estagiario estagiario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assistido_id")
    private Assistido assistido;

    @OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParteEnvolvida> partesEnvolvidas = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ProcessoStatus status;

    @Column(name = "prazo_final")
    private LocalDate prazoFinal;

    @Column(name = "criado_em")
    @CreationTimestamp
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    @UpdateTimestamp
    private LocalDateTime atualizadoEm;

    protected Processo() {
    }

    public void adicionarParteEnvolvida(ParteEnvolvida parte) {
        this.partesEnvolvidas.add(parte);
        parte.setProcesso(this);
    }

    public void atualizarPartesEnvolvidas(List<ParteEnvolvida> novasPartes) {
        this.partesEnvolvidas.clear();
        if (novasPartes != null) {
            novasPartes.forEach(this::adicionarParteEnvolvida);
        }
    }

    /**
     * Gera e atribui o número de identificação interno a este processo.
     * A geração baseia-se na data atual e num número sequencial fornecido.
     *
     * O formato gerado segue o padrão: AAAA.DDMM.XXXX (Ex: 2026.0810.0015).
     *
     * @param sequencial O número sequencial único gerado pelo banco de dados.
     * @throws IllegalStateException se o sequencial fornecido for nulo.
     */
    public void atribuirNumeroDeProcesso(Long sequencial) {
        if (sequencial == null) {
            throw new IllegalStateException("O sequencial não pode ser nulo.");
        }

        String dataFormatada = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.ddMM"));
        this.numeroProcesso = String.format("%s.%04d", dataFormatada, sequencial);
    }

    public void atualizarDados(Advogado advogado, Estagiario estagiario, Assistido assistido,
                          String numeroProcessoPje, AreaDireito areaDireito,
                          String assunto, String vara, Tribunal tribunal,
                          ProcessoStatus status, LocalDate prazoFinal) {

        this.advogado = advogado;
        this.estagiario = estagiario;
        this.assistido = assistido;
        this.numeroProcessoPje = numeroProcessoPje;
        this.areaDireito = areaDireito;
        this.assunto = assunto;
        this.vara = vara;
        this.tribunal = tribunal;
        this.prazoFinal = prazoFinal;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Processo processo)) return false;
        return numeroProcesso != null && Objects.equals(numeroProcesso, processo.getNumeroProcesso());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numeroProcesso);
    }

    @Builder
    public Processo(LocalDate prazoFinal, ProcessoStatus status, Assistido assistido, Estagiario estagiario,
                    Advogado advogado, String vara, String assunto, Tribunal tribunal, AreaDireito areaDireito,
                    String numeroProcessoPje, String numeroProcesso) {

        this.prazoFinal = prazoFinal;
        this.status = status;
        this.assistido = assistido;
        this.estagiario = estagiario;
        this.advogado = advogado;
        this.vara = vara;
        this.assunto = assunto;
        this.tribunal = tribunal;
        this.areaDireito = areaDireito;
        this.numeroProcessoPje = numeroProcessoPje;
        this.numeroProcesso = numeroProcesso;
    }
}
