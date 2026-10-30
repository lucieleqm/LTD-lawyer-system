package com.advocacia.estacio.services;

import java.util.List;

import com.advocacia.estacio.modules.processos.AreaDireito;
import com.advocacia.estacio.modules.processos.ProcessoStatus;
import com.advocacia.estacio.modules.processos.Tribunal;
import com.advocacia.estacio.modules.advogados.AdvogadoService;
import com.advocacia.estacio.modules.assistidos.AssistidoService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.advocacia.estacio.modules.advogados.Advogado;
import com.advocacia.estacio.modules.assistidos.Assistido;
import com.advocacia.estacio.modules.estagiarios.Estagiario;
import com.advocacia.estacio.modules.processos.Processo;
import com.advocacia.estacio.modules.estagiarios.EstagiarioRepository;
import com.advocacia.estacio.modules.processos.ProcessoRepository;
import com.advocacia.estacio.utils.TestUtil;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProcessoServiceTest {
	
	@Autowired
	ProcessoService processoService;
	
	@Autowired
	ProcessoRepository processoRepository;
	
	@Autowired
	AdvogadoService advogadoService;
	
	@Autowired
	AssistidoService assistidoService;
	
	@Autowired
	EstagiarioRepository estagiarioRepository;
	
	@Autowired
	TestUtil testUtil;

	@Test
	@Order(1)
	void deveDeletar_TodosOsDados_AntesDostestes() {
		testUtil.deleteAll();
	}
	
	@Test
	@Order(2)
	@DisplayName("Deve Salvar Processo No Banco de Dados Pelo Service")
	void salvar_processo() {
		assertEquals(0, processoRepository.count());
		
		Long assistidoId = assistidoService.salvar(testUtil.getAssistidoDto()).getId();
		Long advogadoId = advogadoService.salvar(testUtil.getAdvogadoDto()).getId();
		Long estagiarioId = estagiarioRepository.save(testUtil.getEstagiario()).getId();
		
		ProcessoRequestDto request = new ProcessoRequestDto(assistidoId, "2543243", "Seguro de Carro", "23423ee23", "Júlio", advogadoId, estagiarioId, "Civil", "Estadual", "25/10/2025");
		
		Processo processo = processoService.salvar(request);
		
		assertEquals("Ana Carla", processo.getAssistido().getNome());
		assertEquals("Seguro de Carro", processo.getAssunto());
		assertEquals("23423ee23", processo.getVara());
		assertEquals("2025-10-25", processo.getPrazoFinal().toString());
		assertEquals("Júlio", processo.getResponsavel());
		assertEquals("Carlos Silva", processo.getAdvogado().getNome());
		assertNotNull(processo.getNumeroDoProcesso());

		assertEquals(1, processoRepository.count());
	}
	
	@Test
	@DisplayName("Deve Buscar Processos Por Status No Banco de Dados Pelo Service")
	void buscar_processos_por_status() {
		
		Page<ProcessoDto> processos = processoService.buscarProcessosPorStatusDoProcesso("Tramitando", 0, 20);
		
		assertNotNull(processos);
		assertEquals(1, processos.getContent().size());
        assertFalse(processos.isEmpty());
	}

	@Test
	@DisplayName("Deve Buscar TODOS os Processos Paginados e Dados Pelo Service")
	void buscar_todos_processos() {

		Page<Processo> processos = processoService.findAll(0, 20);

		assertNotNull(processos);
		assertFalse(processos.isEmpty());
	}
	
	@Test
	@DisplayName("Deve Buscar Processo Por Numero Do Processo No Banco de Dados Pelo Status")
	void buscar_processo_por_numero_do_processo() {
		
		String numeroDoProcesso = processoRepository.findAll().get(0).getNumeroDoProcesso();
		Page<Processo> processos = processoService.buscarProcesso(numeroDoProcesso, 0, 10);
		
		assertNotNull(processos);
        assertFalse(processos.getContent().isEmpty());
		assertNotNull(processos.getContent().get(0).getAssunto(), "Seguro de Carro");
	}
	
	@Test
	@DisplayName("Deve Atualizar Estagiario No Processo Pelo Service")
	void atualizar_estagiario_no_processo() {
		
		assertEquals(1, processoRepository.count());
		
		Processo processo = processoRepository.findAll().get(0);
		Advogado advogado = advogadoService.buscarPorId(processo.getAdvogado().getId());
		Assistido assistido = assistidoService.buscarPorId(processo.getAssistido().getId());
		Estagiario estagiario = estagiarioRepository.save(testUtil.getEstagiario2());
		
		ProcessoUpdate dto = new ProcessoUpdate(processo.getAssistido().getId(), 
				"23232323", "32323232", "Seguro de celular", "132132", "11/12/2025", advogado.getNome(), 
				advogado.getId(), estagiario.getId(), "Previdenciário", "Federal", 
				"Tramitando", null);
		
		processo = processoService.atualizarProcesso(processo.getId(), dto);
		
		assertEquals(processo.getAssistido(), assistido);
		assertEquals("23232323", processo.getNumeroDoProcesso());
		assertEquals("32323232", processo.getNumeroDoProcessoPje());
		assertEquals("Seguro de celular", processo.getAssunto());
		assertEquals("132132", processo.getVara());
		assertEquals("2025-12-11", processo.getPrazoFinal().toString());
		assertEquals(processo.getResponsavel(), advogado.getNome());
		assertEquals(processo.getAdvogado(), advogado);
		assertEquals(processo.getEstagiario(), estagiario);
		assertEquals("Previdenciário", processo.getAreaDoDireito().getDescricao());
		assertEquals("Federal", processo.getTribunal().getDescricao());
		assertEquals("Tramitando", processo.getStatusDoProcesso().getStatus());
		
		assertEquals(1, processoRepository.count());
	}

	@Test
	@DisplayName("Deve buscar Areas do Direito")
	void buscar_estado_civis() {
		List<AreaDireito> areasDoDireito = processoService.getAreasDoDireito();

		assertEquals(AreaDireito.CIVIL, areasDoDireito.get(0));
		assertEquals(AreaDireito.TRABALHISTA, areasDoDireito.get(1));
		assertEquals(AreaDireito.PREVIDENCIARIO, areasDoDireito.get(2));
	}

	@Test
	@DisplayName("Deve buscar Tribunais")
	void buscar_tribunais() {
		List<Tribunal> tribunais = processoService.getTribunais();

		assertEquals(Tribunal.ESTADUAL, tribunais.get(0));
		assertEquals(Tribunal.FEDERAL, tribunais.get(1));
		assertEquals(Tribunal.TRABALHO, tribunais.get(2));
	}

	@Test
	@DisplayName("Deve buscar Status do Processo")
	void buscar_status_processo() {
		List<ProcessoStatus> processoStatus = processoService.getProcessoStatus();

		assertEquals(ProcessoStatus.TRAMITANDO, processoStatus.get(0));
		assertEquals(ProcessoStatus.SUSPENSO, processoStatus.get(1));
		assertEquals(ProcessoStatus.ARQUIVADO, processoStatus.get(2));
	}
}
