package com.advocacia.estacio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.advocacia.estacio.modules.usuarios.Usuario;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.advocacia.estacio.domain.records.RegistroDto;
import com.advocacia.estacio.infra.security.TokenService;
import com.advocacia.estacio.modules.usuarios.UsuarioRepository;
import com.advocacia.estacio.utils.TestUtil;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TokenServiceTest {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	TestUtil testUtil;
	
	private static String TOKEN = "";
	
	@Test
	@Order(1)
	void deletando_TodosOsDados_DepoisDostestes() {
		testUtil.deleteAll();
	}

	@Test
	@Order(2)
	@DisplayName("Deve gerar Token")
	void gerar_token() {
		usuarioRepository.save(testUtil.getUsuarioAuth());
		
		RegistroDto dto = testUtil.getRegistroDtos().get(0);
		
		var usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
		
		var auth = authenticationManager.authenticate(usernamePassword);
		
		String token = tokenService.generateToken((Usuario) auth.getPrincipal());
		TOKEN = token;
		assertNotNull(token);
	}
	
	@Test
	@Order(3)
	@DisplayName("Deve validar Token Pelo Service")
	void validar_token() {
		String login = tokenService.validateToken(TOKEN);
		
		Usuario userAuth = (Usuario) usuarioRepository.findByLogin(login);
		
		assertEquals(userAuth.getLogin(), testUtil.getRegistroDtos().get(0).login());
	}

}
