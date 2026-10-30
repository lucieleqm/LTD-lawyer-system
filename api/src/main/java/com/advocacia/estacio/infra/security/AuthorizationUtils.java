package com.advocacia.estacio.infra.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Avaliador de permissões e regras de segurança (Authorization).
 * Utilizado para verificar se o usuário logado possui os requisitos para acessar um recurso.
 * Responde à pergunta: "O usuário atual PODE realizar esta ação?"
 * Ideal para ser usado em anotações @PreAuthorize("@authz.metodo()") ou regras de Service.
 */
@Component("authz")
@RequiredArgsConstructor
public class AuthorizationUtils {

    private final SecurityUtils securityUtils;

    public boolean isResourceOwner(Long idDono) {
        return securityUtils.getIdUsuarioLogado().equals(idDono);
    }

    public boolean isAdmin() {
        return securityUtils.getUsuarioLogado().getAuthorities().stream()
                .allMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }
}