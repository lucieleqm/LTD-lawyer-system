package com.advocacia.estacio.infra.security;

import java.io.IOException;

import com.advocacia.estacio.modules.usuarios.UsuarioRole;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	private final TokenService tokenService;

	public SecurityFilter(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var token = recoverToken(request);
		if(token != null) {
			DecodedJWT decodedJWT = tokenService.validateAndDecodeToken(token);

			if(decodedJWT != null) {
				String login = decodedJWT.getSubject();
				Long id = decodedJWT.getClaim("id").asLong();
				Long pessoaId = decodedJWT.getClaim("pessoaId").asLong();
				UsuarioRole role = UsuarioRole.valueOf(decodedJWT.getClaim("role").asString());

				CustomUserDetails customUserDetails = new CustomUserDetails(id, pessoaId, login, role);

				var authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		filterChain.doFilter(request, response);
	}
	
	private String recoverToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		if(authHeader == null) return null;
		return authHeader.replace("Bearer ", "");
	}

}
