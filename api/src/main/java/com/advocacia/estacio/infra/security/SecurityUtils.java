package com.advocacia.estacio.infra.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.security.access.AccessDeniedException;

/**
 * Utilitário responsável exclusivamente por extrair informações do contexto de segurança do Spring.
 * Responde à pergunta: "Quem está logado agora?"
 */
@Component
public class SecurityUtils {

    public CustomUserDetails getUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails customUser) {
            return customUser;
        }
        throw new AccessDeniedException("Usuário não está autenticado");
    }

    public Long getIdUsuarioLogado() {
        return getUsuarioLogado().getId();
    }

    public  Long getIdPessoaLogada() { return  getUsuarioLogado().getPessoaId(); }
}
