package com.advocacia.estacio.modules.usuarios;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public interface UsuarioDTO {

    @Schema(name = "UsuarioCreateRequest")
    record CreateRequest(
            @NotBlank String login,
            @NotBlank String password
    ) {}

    @Schema(name = "UsuarioResponse")
    record Response(
            Long id,
            String login,
            UsuarioRole role,
            UsuarioStatus status,
            LocalDateTime criadoEm,
            LocalDateTime desativadoEm,
            LocalDateTime bloqueadoEm
    ) {
        public Response(Usuario usuario) {
            this(
                    usuario.getId(),
                    usuario.getLogin(),
                    usuario.getRole(),
                    usuario.getStatus(),
                    usuario.getCriadoEm(),
                    usuario.getDesativadoEm(),
                    usuario.getBloqueadoEm()
            );
        }
    }

    record UpdateRequest(
            String login,
            UsuarioRole role,
            UsuarioStatus status
    ) {}

    record ResetPasswordRequest(
            @NotBlank String login,
            @NotBlank String novaSenha
    ) {}
}
