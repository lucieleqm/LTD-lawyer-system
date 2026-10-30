package com.advocacia.estacio.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;

	public String generateToken(CustomUserDetails user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.create()
					.withIssuer("lawyer-system")
					.withSubject(user.getUsername())
					.withClaim("id", user.getId())
					.withClaim("pessoaId", user.getPessoaId())
					.withClaim("role", user.getRole().name())
					.withExpiresAt(getExpirationDate())
					.sign(algorithm);
			
		} catch(JWTCreationException e) {
			throw new RuntimeException("Error ao gerar token " + e);
		}
	}
	
	public DecodedJWT validateAndDecodeToken(String token) {
		try {
			
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("lawyer-system")
					.build()
					.verify(token);
		} catch(JWTVerificationException e) {
			return null;
		}
	}
	
	public Instant getExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
