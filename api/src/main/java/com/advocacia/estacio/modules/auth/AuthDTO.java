package com.advocacia.estacio.modules.auth;

import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public interface AuthDTO {
    record LoginRequest(
            @NotBlank(message = "Campo obrigatório")  String login,
            @NotBlank(message = "Campo obrigatório") String password
    ) {}

    record LoginResponse(
            String token,
            String tipo,
            Instant expiraEm
    ) {}
}
