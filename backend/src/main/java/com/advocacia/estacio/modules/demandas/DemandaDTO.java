package com.advocacia.estacio.modules.demandas;

import com.advocacia.estacio.modules.advogados.Advogado;
import com.advocacia.estacio.modules.estagiarios.Estagiario;
import com.advocacia.estacio.modules.professores.Professor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface DemandaDTO {
    @Schema(name = "DemandaCreateRequest")
    record Request(
            @NotNull(message = "O advogado é obrigatório") Long advogadoId,
            @NotNull(message = "O estagiário é obrigatório") Long estagiarioId,
            @NotNull(message = "O professor é obrigatório") Long professorId,
            @Schema(example = "Cliente sofreu acidente de trânsito e a seguradora se recusa a pagar.")
            @NotBlank(message = "A descrição é obrigatória") String descricao,
            @NotNull(message = "Os dias adicionais é obrigatório") Integer diasAdicionais,
            @NotNull(message = "O prazo da documentação é obrigatória")  LocalDate prazoDocumentos,
            @NotNull(message = "O prazo final é obrigatório")  EtapaDemanda etapaAtual
    ) {
        public Demanda toEntity(Advogado advogado, Estagiario estagiario, Professor professor) {
            LocalDate prazoFinal = this.prazoDocumentos.plusDays(this.diasAdicionais);

            return Demanda.builder()
                    .advogado(advogado)
                    .estagiario(estagiario)
                    .professor(professor)
                    .descricao(this.descricao)
                    .prazoFinal(prazoFinal)
                    .prazoDocumentos(this.prazoDocumentos)
                    .etapaAtual(this.etapaAtual)
                    .build();
        }
    }

    @Schema(name = "DemandaResponse")
    record Response(
            Long id,
            String descricao,
            LocalDate prazoFinal,
            LocalDate prazoDocumentos,
            EtapaDemanda etapaAtual,
            Tempestividade tempestividade,
            LocalDateTime dataAbertura,
            LocalDateTime ultimaAtualizacao,
            Long advogadoId,
            String advogadoNome,
            Long estagiarioId,
            String estagiarioNome,
            Long professorId,
            String professorNome
    ) {
        public Response(Demanda demanda) {
            this(
                    demanda.getId(),
                    demanda.getDescricao(),
                    demanda.getPrazoFinal(),
                    demanda.getPrazoDocumentos(),
                    demanda.getEtapaAtual(),
                    demanda.getTempestividade(),
                    demanda.getDataAbertura(),
                    demanda.getUltimaAtualizacao(),
                    demanda.getAdvogado().getId(),
                    demanda.getAdvogado().getPessoa().getNome(),
                    demanda.getEstagiario().getId(),
                    demanda.getEstagiario().getPessoa().getNome(),
                    demanda.getProfessor().getId(),
                    demanda.getProfessor().getPessoa().getNome()
            );
        }
    }

    @Schema(name = "DemandaListResponse")
    record ListResponse(
            Long id,
            String descricao,
            LocalDate prazoFinal,
            LocalDate prazoDocumentos,
            String nomeAdvogado,
            String nomeEstagiario,
            String nomeProfessor,
            EtapaDemanda etapaAtual,
            Tempestividade tempestividade
    ) {
        public ListResponse(Demanda demanda) {
            this( demanda.getId(),
                    demanda.getDescricao(),
                    demanda.getPrazoFinal(),
                    demanda.getPrazoDocumentos(),
                    demanda.getAdvogado().getPessoa().getNome(),
                    demanda.getEstagiario().getPessoa().getNome(),
                    demanda.getProfessor().getPessoa().getNome(),
                    demanda.getEtapaAtual(),
                    demanda.getTempestividade()
            );
        }
    }

    @Schema(name = "DemandaSearchFilter")
    record SearchFilter(
            String termo,
            EtapaDemanda etapaAtual,
            Tempestividade tempestividade
    ) {}

    @Schema(name = "DemandaUpdateRequest")
    record UpdateRequest(
            @NotNull(message = "O advogado é obrigatório") Long advogadoId,
            @NotNull(message = "O estagiário é obrigatório") Long estagiarioId,
            @NotNull(message = "O professor é obrigatório") Long professorId,
            @NotBlank(message = "A descrição é obrigatória") String descricao,
            @NotNull(message = "O campo 'dias adicionais' é obrigatório") Integer diasAdicionais,
            @NotNull(message = "O prazo da documentação é obrigatória")  LocalDate prazoDocumentos,
            @NotNull(message = "O prazo final é obrigatório")  EtapaDemanda etapaAtual
    ) {}
}
