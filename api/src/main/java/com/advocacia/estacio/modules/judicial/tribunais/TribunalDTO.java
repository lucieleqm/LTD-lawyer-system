package com.advocacia.estacio.modules.judicial.tribunais;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public interface TribunalDTO {
    @Schema(name = "TribunalRequest")
    record Request(
            @NotBlank @Size(max = 120) String nome,
            @NotNull EsferaJustica esfera,
            String sigla
    ) {
        public Tribunal toEntity() {
            return Tribunal.builder()
                    .nome(nome.strip())
                    .sigla(sigla.strip())
                    .esfera(esfera)
                    .build();
        }
    }

    @Schema(name = "TribunalResponse")
    record Response(
            Long id,
            String nome,
            String sigla,
            EsferaJustica esfera,
            LocalDateTime criadoEm,
            LocalDateTime atualizadoEm,
            String criadoPor,
            String atualizadoPor,
            boolean ativo
    ) {
        public Response(Tribunal tribunal) {

            this(
                    tribunal.getId(),
                    tribunal.getNome(),
                    tribunal.getSigla(),
                    tribunal.getEsfera(),
                    tribunal.getCriadoEm(),
                    tribunal.getAtualizadoEm(),
                    tribunal.getCriadoPor(),
                    tribunal.getAtualizadoPor(),
                    tribunal.isAtivo()
            );
        }
    }

    @Schema(name = "TribunalSummary")
    record Summary(Long id, String sigla, String nome) {
        public Summary(Tribunal tribunal) {
            this(
                    tribunal.getId(),
                    tribunal.getSigla(),
                    tribunal.getNome());
        }
    }
}
