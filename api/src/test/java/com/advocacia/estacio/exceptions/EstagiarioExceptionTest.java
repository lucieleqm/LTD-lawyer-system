package com.advocacia.estacio.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.advocacia.estacio.modules.estagiarios.EstagiarioService;

@SpringBootTest
class EstagiarioExceptionTest {
	
	@Autowired
	EstagiarioService estagiarioService;

	@Test
	@DisplayName("Deve lançar exceção EntidadeNaoEncontradaException ao buscar Estagiario por Id")
	void entidadeNaoEncontradaException() {
		assertThrows(EntidadeNaoEncontradaException.class, () -> estagiarioService.buscarPorId(70L));
	}
}
