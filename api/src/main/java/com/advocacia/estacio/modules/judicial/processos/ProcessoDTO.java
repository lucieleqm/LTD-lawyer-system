package com.advocacia.estacio.modules.judicial.processos;

import com.advocacia.estacio.modules.judicial.areas_direito.AreaDireito;
import com.advocacia.estacio.modules.membros.advogados.Advogado;
import com.advocacia.estacio.modules.atendimento.assistidos.Assistido;
import com.advocacia.estacio.modules.membros.estagiarios.Estagiario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface ProcessoDTO {
    @Schema(name = "ParteEnvolvidaRequest")
    record ParteEnvolvidaRequest(
            @NotBlank String nome,
            @NotNull PapelNoProcesso papel,
            String especificacaoPapel
    ) {
        public ParteEnvolvida toEntity() {
            return ParteEnvolvida.builder()
                    .nome(this.nome)
                    .papel(this.papel)
                    .especificacaoPapel(this.especificacaoPapel)
                    .build();
        }
    }

    @Schema(name = "ProcessoCreateRequest")
    record CreateRequest(
            @NotNull Long advogadoId,
            @NotNull Long estagiarioId,
            @NotNull Long assistidoId,
            @NotNull Long areaDireitoId,
            String numeroProcessoPje,
            String assunto,
            String vara,
            Tribunal tribunal,
            LocalDate prazoFinal,
            List<ParteEnvolvidaRequest> partesEnvolvidas
    ) {
        public Processo toEntity(Assistido assistido, Advogado advogado, Estagiario estagiario, AreaDireito areaDireito) {
            Processo processo = Processo.builder()
                    .prazoFinal(prazoFinal)
                    .status(ProcessoStatus.TRAMITANDO)
                    .assistido(assistido)
                    .estagiario(estagiario)
                    .advogado(advogado)
                    .vara(vara)
                    .assunto(assunto)
                    .tribunal(tribunal)
                    .areaDireito(areaDireito)
                    .numeroProcessoPje(numeroProcessoPje)
                    .build();

            if(this.partesEnvolvidas != null) {
                this.partesEnvolvidas.forEach(reqParte -> {
                    processo.adicionarParteEnvolvida(reqParte.toEntity());
                });
            }
            return processo;
        }
    }

    @Schema(name = "ParteEnvolvidaResponse")
    public record ParteEnvolvidaResponse(
            Long id,
            String nome,
            String papel,
            String especificacaoPapel
    ) {
        public ParteEnvolvidaResponse(ParteEnvolvida entidade) {
            this(
                    entidade.getId(),
                    entidade.getNome(),
                    entidade.getPapel().name(),
                    entidade.getEspecificacaoPapel()
            );
        }
    }

    @Schema(name = "ProcessoResponse")
    record Response(
            Long id,
            String numeroProcesso,
            String numeroProcessoPje,
            AreaDireito areaDireito,
            Tribunal tribunal,
            String vara,
            String assunto,
            ProcessoStatus status,
            LocalDate prazoFinal,
            Long advogadoId,
            String advogadoNome,
            Long assistidoId,
            String assistidoNome,
            Long estagiarioId,
            String estagiarioNome,
            List<ParteEnvolvidaResponse> partesEnvolvidas,
            LocalDateTime atualizadoEm,
            LocalDateTime criadoEm
    ){
        public Response(Processo processo) {
            this(
                    processo.getId(),
                    processo.getNumeroProcesso(),
                    processo.getNumeroProcessoPje(),
                    processo.getAreaDireito(),
                    processo.getTribunal(),
                    processo.getVara(),
                    processo.getAssunto(),
                    processo.getStatus(),
                    processo.getPrazoFinal(),
                    processo.getAdvogado().getId(),
                    processo.getAdvogado().getPessoa().getNome(),
                    processo.getAssistido().getId(),
                    processo.getAssistido().getPessoa().getNome(),
                    processo.getEstagiario().getId(),
                    processo.getEstagiario().getPessoa().getNome(),
                    processo.getPartesEnvolvidas() != null
                            ? processo.getPartesEnvolvidas().stream()
                            .map(ParteEnvolvidaResponse::new)
                            .toList()
                            : new ArrayList<>(),
                    processo.getAtualizadoEm(),
                    processo.getCriadoEm()
            );
        }
    }

    @Schema(name = "ProcessoSearchFilter")
    record SearchFilter(
            String termo,
            AreaDireito areaDireito,
            Tribunal tribunal,
            ProcessoStatus status
    ) {}

    @Schema(name = "ProcessoListResponse")
    record ListResponse(
            Long id,
            String numeroProcesso,
            String numeroProcessoPje,
            Tribunal tribunal,
            AreaDireito areaDireito,
            ProcessoStatus status,
            String assistidoNome,
            String advogadoNome,
            String estagiarioNome,
            LocalDate prazoFinal
    ) {
        public ListResponse(Processo processo) {
            this(
                    processo.getId(),
                    processo.getNumeroProcesso(),
                    processo.getNumeroProcessoPje(),
                    processo.getTribunal(),
                    processo.getAreaDireito(),
                    processo.getStatus(),
                    processo.getAssistido().getPessoa().getNome(),
                    processo.getAdvogado().getPessoa().getNome(),
                    processo.getEstagiario().getPessoa().getNome(),
                    processo.getPrazoFinal()
            );
        }
    }

    @Schema(name = "ProcessoUpdateRequest")
    record UpdateRequest(
            @NotNull Long advogadoId,
            @NotNull Long estagiarioId,
            @NotNull Long assistidoId,
            String numeroProcessoPje,
            AreaDireito areaDireito,
            String assunto,
            String vara,
            Tribunal tribunal,
            ProcessoStatus status,
            LocalDate prazoFinal,
            @Valid List<ParteEnvolvidaRequest> partesEnvolvidas
    ) {}
}
