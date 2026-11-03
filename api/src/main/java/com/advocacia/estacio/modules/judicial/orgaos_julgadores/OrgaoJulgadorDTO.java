package com.advocacia.estacio.modules.judicial.orgaos_julgadores;

import com.advocacia.estacio.modules.judicial.tribunais.Tribunal;
import com.advocacia.estacio.modules.judicial.tribunais.TribunalDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public interface OrgaoJulgadorDTO {
    @Schema(name = "OrgaoJulgadorRequest")
    record Request(
            @NotBlank @Size(max = 120) String nome,
            @NotNull Tribunal tribunal,
            @NotNull Integer instancia
            ) {
        public OrgaoJulgador toEntity() {
            return OrgaoJulgador.builder()
                    .nome(nome.strip())
                    .tribunal(tribunal)
                    .instancia(instancia)
                    .build();
        }
    }

    @Schema(name = "OrgaoJulgadorResponse")
    record Response(
            Long id,
            String nome,
            TribunalDTO.Summary tribunal,
            Integer instancia,
            LocalDateTime criadoEm,
            LocalDateTime atualizadoEm,
            String criadoPor,
            String atualizadoPor,
            boolean ativo
    ) {
        public Response(OrgaoJulgador orgao) {

            this(
                    orgao.getId(),
                    orgao.getNome(),
                    new TribunalDTO.Summary(orgao.getTribunal()),
                    orgao.getInstancia(),
                    orgao.getCriadoEm(),
                    orgao.getAtualizadoEm(),
                    orgao.getCriadoPor(),
                    orgao.getAtualizadoPor(),
                    orgao.isAtivo()
            );
        }
    }

    @Schema(name = "OrgaoJulgadorSummary")
    record Summary(Long id, String nome) {
        public Summary(OrgaoJulgador orgao) {
            this(orgao.getId(), orgao.getNome());
        }
    }
}
