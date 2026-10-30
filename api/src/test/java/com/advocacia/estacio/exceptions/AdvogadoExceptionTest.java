package com.advocacia.estacio.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.advocacia.estacio.modules.advogados.AdvogadoService;

@SpringBootTest
class AdvogadoExceptionTest {
	
	@Autowired
	AdvogadoService advogadoService;

	@Test
	@DisplayName("Deve lançar exceção EntidadeNaoEncontradaException ao buscar advogado por Id")
	void entidadeNaoEncontradaException() {
		assertThrows(EntidadeNaoEncontradaException.class, () -> advogadoService.buscarPorId(70L));
	}
}
