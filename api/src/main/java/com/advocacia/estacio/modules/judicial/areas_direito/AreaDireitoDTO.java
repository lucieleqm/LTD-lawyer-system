package com.advocacia.estacio.modules.judicial.areas_direito;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public interface AreaDireitoDTO {
    @Schema(name = "AreaDireitoRequest")
    record Request(
            @NotBlank @Size(max = 120) String nome
    ) {
        public AreaDireito toEntity() {
            return AreaDireito.builder()
                    .nome(nome.strip())
                    .build();
        }
    }

    @Schema(name = "AreaDireitoResponse")
    record Response(
            Long id,
            String nome,
            LocalDateTime criadoEm,
            LocalDateTime atualizadoEm,
            String criadoPor,
            String atualizadoPor,
            boolean ativo
    ) {
        public Response(AreaDireito area) {

            this(
                    area.getId(),
                    area.getNome(),
                    area.getCriadoEm(),
                    area.getAtualizadoEm(),
                    area.getCriadoPor(),
                    area.getAtualizadoPor(),
                    area.isAtivo()
            );
        }
    }

    @Schema(name = "AreaDireitoSummary")
    record Summary(Long id, String nome) {
        public Summary(AreaDireito area) {
            this(area.getId(), area.getNome());
        }
    }
}
