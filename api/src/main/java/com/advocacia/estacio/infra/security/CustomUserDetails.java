package com.advocacia.estacio.infra.security;

import com.advocacia.estacio.modules.usuarios.UsuarioRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    @Getter
    private final Long id;
    @Getter
    private final Long pessoaId;
    private final String login;
    private final String password;
    @Getter
    private final UsuarioRole role;
    private final boolean isBloqueado;
    private final boolean isAtivo;

    // Construtor para o LOGIN (vem do banco com senha)
    public CustomUserDetails(Long id, Long pessoaId, String login, String password, UsuarioRole role, boolean isBloqueado, boolean isAtivo) {
        this.id = id;
        this.pessoaId = pessoaId;
        this.login = login;
        this.password = password;
        this.role = role;
        this.isBloqueado = isBloqueado;
        this.isAtivo = isAtivo;
    }

    // Construtor para o FILTRO (vem do token, sem senha)
    public CustomUserDetails(Long id, Long pessoaId, String login, UsuarioRole role) {
        this.id = id;
        this.pessoaId = pessoaId;
        this.login = login;
        this.password = null;
        this.role = role;
        this.isBloqueado = false;
        this.isAtivo = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return login;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBloqueado;
    }

    @Override
    public boolean isEnabled() {
        return isAtivo;
    }
}
