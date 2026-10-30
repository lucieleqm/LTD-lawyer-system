package com.advocacia.estacio.controllers;

import com.advocacia.estacio.modules.demandas.entities.Demanda;
import com.advocacia.estacio.modules.estagiarios.Estagiario;
import com.advocacia.estacio.modules.professores.Professor;
import com.advocacia.estacio.modules.demandas.repositories.DemandaRepository;
import com.advocacia.estacio.modules.estagiarios.EstagiarioRepository;

import com.advocacia.estacio.modules.advogados.AdvogadoService;
import com.advocacia.estacio.services.AtorService;
import com.advocacia.estacio.modules.demandas.services.DemandaService;
import com.advocacia.estacio.utils.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DemandaComentarioControllerTest {
	
	@Autowired
	DemandaRepository demandaRepository;
	
	@Autowired
	EstagiarioRepository estagiarioRepository;

	@Autowired
	DemandaRespondeRepository demandaRespondeRepository;

	@Autowired
	DemandaService demandaService;

	@Autowired
	AtorService atorService;

	@Autowired
	AdvogadoService advogadoService;

	@Autowired
	TestUtil testUtil;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;

	private static final String URI = "/demandas/responde";

	private static String TOKEN = "";
	
	@Test
	@Order(1)
	void preparando_ambiente_de_testes() {
		testUtil.deleteAll();
		
		TOKEN = testUtil.getToken();
	}
	
	@Test
	@Order(2)
	@DisplayName("Deve Salvar Demanda Responde No Banco de Dados Pelo Controller")
	void deveSalvar_DemandaResponde_NoBancoDeDados_PeloController() throws Exception {

		Professor professor = (Professor) atorService.salvar(testUtil.getAtores().get(2));

		assertEquals(0, demandaRespondeRepository.count());

		Estagiario estagiario = estagiarioRepository.save(testUtil.getEstagiario());

		Long advogadoId = advogadoService.salvar(testUtil.getAdvogadoDto()).getId();

		DemandaDto demandaDto = new DemandaDto(null, "Atualizar Documentos", estagiario.getId(), professor.getId(), advogadoId, "Corrigido", "Aguardando Professor", "Aguardando Advogado","02/11/2025", 10, "Dentro do Prazo");
		Demanda demanda = demandaService.salvar(demandaDto);

		DemandaRespondeDto demandaRespondeDto = new DemandaRespondeDto(null, demanda.getId(), estagiario.getId(), "Documentação completa", "Estagiário");

		String jsonRequest = objectMapper.writeValueAsString(demandaRespondeDto);

		mockMvc.perform(post(URI + "/")
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.resposta", equalTo(demandaRespondeDto.getResposta())))
				.andExpect(jsonPath("$.respondidoPor", equalTo(demandaRespondeDto.getRespondidoPor())));
		
		assertEquals(1, demandaRespondeRepository.count());
	}

	@Test
	@DisplayName("Deve Buscar Demanda Por Id No Banco de Dados Pelo Controller")
	void deve_buscar_DemandaResponde_por_demandaId_PeloController() throws Exception {

		Long demandaId = demandaRepository.findAll().get(0).getId();

		mockMvc.perform(get(URI + "/demanda/" + demandaId)
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()", equalTo(1)));
	}
}
