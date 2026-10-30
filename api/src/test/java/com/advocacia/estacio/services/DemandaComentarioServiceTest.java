package com.advocacia.estacio.services;

import com.advocacia.estacio.modules.demandas.entities.Demanda;
import com.advocacia.estacio.modules.demandas.entities.DemandaComentario;
import com.advocacia.estacio.modules.estagiarios.Estagiario;
import com.advocacia.estacio.modules.professores.Professor;
import com.advocacia.estacio.modules.demandas.enums.RespondidoPor;
import com.advocacia.estacio.modules.advogados.AdvogadoService;
import com.advocacia.estacio.modules.demandas.services.DemandaService;
import com.advocacia.estacio.modules.estagiarios.EstagiarioRepository;
import com.advocacia.estacio.utils.TestUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DemandaComentarioServiceTest {
	
	@Autowired
	EstagiarioRepository estagiarioRepository;
	
	@Autowired
	DemandaRespondeRepository demandaRespondeRepository;

	@Autowired
    DemandaComentarioService demandaComentarioService;
	
	@Autowired
	DemandaService demandaService;

	@Autowired
	AtorService atorService;

	@Autowired
	AdvogadoService advogadoService;
	
	@Autowired
	TestUtil testUtil;

	Professor professor;
	
	@Test
	@Order(1)
	void deveDeletar_TodosOsDados_AntesDostestes() {

		testUtil.deleteAll();
	}

	@Test
	@Order(2)
	@DisplayName("Deve Salvar Demanda Resposta No Banco de Dados Pelo Service")
	void salvar_demanda_resposta() {
		
		assertEquals(0, demandaRespondeRepository.count());

		this.professor = (Professor) atorService.salvar(testUtil.getAtores().get(2));
		
		Estagiario estagiario = estagiarioRepository.save(testUtil.getEstagiario());

		Long advogadoId = advogadoService.salvar(testUtil.getAdvogadoDto()).getId();

		DemandaDto demandaDto = new DemandaDto(null, "Atualizar Documentos", estagiario.getId(), this.professor.getId(), advogadoId, "Corrigido", "Aguardando Professor", "Aguardando Advogado", "02/11/2025", 10, "Dentro do Prazo");
		Demanda demanda = demandaService.salvar(demandaDto);

		DemandaRespondeDto demandaRespondeDto = new DemandaRespondeDto(null, demanda.getId(), estagiario.getId(), "Documentação completa", "Estagiário");
		DemandaComentario demandaComentario = demandaComentarioService.salvar(demandaRespondeDto);

		assertNotNull(demandaComentario);
		assertNotNull(demandaComentario.getId());
		assertEquals(demandaRespondeDto.getResposta(), demandaComentario.getResposta());
		assertEquals(estagiario.getId(), demandaComentario.getEstagiario().getId());
		assertEquals(demanda.getId(), demandaComentario.getDemanda().getId());
		assertEquals(RespondidoPor.ESTAGIARIO, demandaComentario.getRespondidoPor());

		assertEquals(1, demandaRespondeRepository.count());
	}

	@Test
	@DisplayName("Deve Buscar Demanda Por Id No Banco de Dados Pelo Service")
	void buscar_demanda_por_demandaId() {

		Long demandaId = demandaRespondeRepository.findAll().get(0).getDemanda().getId();
		Page<DemandaRespondeDto> pages = demandaComentarioService.buscarDemandasRespostasPorDemandaId(demandaId, 0, 20);

		assertNotNull(pages);
		assertEquals(1, pages.getContent().size());
	}
}
