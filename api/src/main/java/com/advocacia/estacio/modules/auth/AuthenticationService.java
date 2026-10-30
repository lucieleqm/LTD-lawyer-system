package com.advocacia.estacio.modules.auth;

import com.advocacia.estacio.infra.security.CustomUserDetails;
import com.advocacia.estacio.infra.security.TokenService;
import com.advocacia.estacio.modules.usuarios.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthDTO.LoginResponse login(AuthDTO.LoginRequest dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        CustomUserDetails customUser = (CustomUserDetails) auth.getPrincipal();
        String token = tokenService.generateToken(customUser);
        Instant expiracao = tokenService.getExpirationDate();

        return new AuthDTO.LoginResponse(
                token,
                "Bearer",
                expiracao
        );
    }
}
