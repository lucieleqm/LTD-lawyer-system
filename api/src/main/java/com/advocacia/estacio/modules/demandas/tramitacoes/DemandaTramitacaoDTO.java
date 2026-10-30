package com.advocacia.estacio.modules.demandas.tramitacoes;

import com.advocacia.estacio.modules.pessoas.Pessoa;
import com.advocacia.estacio.modules.usuarios.UsuarioRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public interface DemandaTramitacaoDTO {

    @Schema(name = "DemandaTramitacaoCreateRequest")
    record CreateRequest(
            @NotNull TipoTramitacao tipoTramitacao,
            String observacoes,
            String linkAnexo
    ) {
        public DemandaTramitacao toEntity(Pessoa responsavel) {
            return DemandaTramitacao.builder()
                    .responsavel(responsavel)
                    .tipoTramitacao(this.tipoTramitacao)
                    .observacoes(this.observacoes)
                    .linkAnexo(this.linkAnexo)
                    .build();
        }
    }

    @Schema(name = "DemandaTramitacaoResponse")
    record Response(
            Long id,
            String responsavelNome,
            UsuarioRole responsavelRole,
            TipoTramitacao tipoTramitacao,
            String observacoes,
            String linkAnexo,
            LocalDateTime criadoEm
    ) {
        public Response(DemandaTramitacao tramitacao) {
            this(
                    tramitacao.getId(),
                    tramitacao.getResponsavel().getNome(),
                    tramitacao.getResponsavel().getUsuario().getRole(),
                    tramitacao.getTipoTramitacao(),
                    tramitacao.getObservacoes(),
                    tramitacao.getLinkAnexo(),
                    tramitacao.getCriadoEm()
            );
        }
    }

    @Schema(name = "DemandaTramitacaoDisponivel")
    record Disponivel(TipoTramitacao tipo, String descricao ) {}
}
