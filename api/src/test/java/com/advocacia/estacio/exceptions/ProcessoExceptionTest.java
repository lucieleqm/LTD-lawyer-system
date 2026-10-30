package com.advocacia.estacio.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.advocacia.estacio.modules.processos.Processo;
import com.advocacia.estacio.modules.processos.ProcessoRepository;
import com.advocacia.estacio.services.ProcessoService;
import com.advocacia.estacio.utils.TestUtil;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProcessoExceptionTest {
	
	@Autowired
	ProcessoService processoService;
	
	@Autowired
	ProcessoRepository processoRepository;
	
	@Autowired
	TestUtil testUtil;
	
	Processo processo;
	
	@BeforeEach
	void setUp() {
		testUtil.deleteAll();
	}

	@Test
	@DisplayName("Deve lançar exceção EntidadeNaoEncontradaException ao buscar Processo por Id")
	void entidadeNaoEncontradaException_por_id() {
		assertThrows(EntidadeNaoEncontradaException.class, () -> processoService.buscarPorId(70L));
	}
	
	@Test
	@DisplayName("Deve lançar exceção EntidadeNaoEncontradaException ao buscar Processo por Número Do Processo")
	void entidadeNaoEncontradaException_por_numero_do_processo() {
		assertThrows(EntidadeNaoEncontradaException.class, () -> processoService.buscarPorNumeroDoProcesso("123432"));
	}
	
	@Test
	@DisplayName("Deve lançar exceção NumeroDoProcessoExistenteException ao tentar validar Numero Do Processo já cadastrado")
	void NumeroDoProcessoExistenteException_apos_validar() {

		Processo processo = processoRepository.save(testUtil.getProcesso());
		
		assertTrue(processoRepository.existsByNumeroDoProcesso(processo.getNumeroDoProcesso()));
		assertThrows(NumeroDoProcessoExistenteException.class, () -> processoService.validarProcesso(processo));
	}
	
	@Test
	@DisplayName("Deve lançar exceção NumeroDoProcessoExistenteException ao tentar salvar Processo Com Numero Do Processo já cadastrado")
	void NumeroDoProcessoExistenteException_apos_salvar() {

		Processo processo = processoRepository.save(testUtil.getProcesso());
		ProcessoRequestDto request = new ProcessoRequestDto(processo);
		
		assertTrue(processoRepository.existsByNumeroDoProcesso(processo.getNumeroDoProcesso()));
		assertThrows(NumeroDoProcessoExistenteException.class, () -> testUtil.salvar(request));
	}
}
