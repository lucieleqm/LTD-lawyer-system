package com.advocacia.estacio.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.advocacia.estacio.modules.demandas.entities.Demanda;
import com.advocacia.estacio.modules.professores.Professor;
import com.advocacia.estacio.modules.demandas.enums.EtapaDemanda;
import com.advocacia.estacio.modules.advogados.AdvogadoRepository;
import com.advocacia.estacio.repositories.AtorRepository;
import com.advocacia.estacio.modules.advogados.AdvogadoService;
import com.advocacia.estacio.services.AtorService;
import com.advocacia.estacio.modules.demandas.services.DemandaService;
import com.advocacia.estacio.modules.estagiarios.EstagiarioService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.advocacia.estacio.modules.estagiarios.Estagiario;
import com.advocacia.estacio.modules.demandas.repositories.DemandaRepository;
import com.advocacia.estacio.modules.estagiarios.EstagiarioRepository;
import com.advocacia.estacio.utils.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DemandaControllerTest {
	
	@Autowired
	DemandaRepository demandaRepository;
	
	@Autowired
	EstagiarioRepository estagiarioRepository;

	@Autowired
	EstagiarioService estagiarioService;

	@Autowired
	AtorRepository atorRepository;

	@Autowired
	AtorService atorService;

	@Autowired
	AdvogadoService advogadoService;

	@Autowired
	AdvogadoRepository advogadoRepository;

	@Autowired
	DemandaService demandaService;
	
	@Autowired
	TestUtil testUtil;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private static final String URI = "/demandas";
	
	private static String TOKEN = "";
	
	@Test
	@Order(1)
	void preparando_ambiente_de_testes() {
		testUtil.deleteAll();
		
		TOKEN = testUtil.getToken();
	}
	
	@Test
	@Order(2)
	@DisplayName("Deve Salvar Demanda No Banco de Dados Pelo Controller")
	void salvar_demanda() throws Exception {
		
		assertEquals(0, demandaRepository.count());

		Professor professor = (Professor) atorService.salvar(testUtil.getAtores().get(2));
		
		Estagiario estagiario = estagiarioRepository.save(testUtil.getEstagiario());

		Long advogadoId = advogadoService.salvar(testUtil.getAdvogadoDto()).getId();
		
		DemandaDto demandaDto = new DemandaDto(null, "Atualizar Processos", estagiario.getId(), professor.getId(), advogadoId, "Em Correção", "Aguardando Professor", "Aguardando Advogado", "02/11/2025", 10, "Dentro do Prazo");
		
		String jsonRequest = objectMapper.writeValueAsString(demandaDto);
		
		mockMvc.perform(post(URI + "/")
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.demanda", equalTo("Atualizar Processos")))
				.andExpect(jsonPath("$.estagiarioNome", equalTo("Pedro Lucas")))
				.andExpect(jsonPath("$.estagiarioId", equalTo(estagiario.getId().intValue())))
				//.andExpect(jsonPath("$.professorId", equalTo(professorId.intValue())))
				.andExpect(jsonPath("$.advogadoId", equalTo(advogadoId.intValue())))
				.andExpect(jsonPath("$.demandaStatusAluno", equalTo("Aguardando Aluno")))
				.andExpect(jsonPath("$.demandaStatusProfessor", equalTo("Null")))
				.andExpect(jsonPath("$.demandaStatusAdvogado", equalTo("Null")))
				.andExpect(jsonPath("$.prazo", equalTo("12/11/2025")))
				.andExpect(jsonPath("$.tempestividade", equalTo("Dentro do Prazo")));
		
		assertEquals(1, demandaRepository.count());
	}

	@Test
	@Order(3)
	@DisplayName("Deve Buscar Demanda Por Status No Banco de Dados Pelo Controller")
	void buscar_demanda_por_status() throws Exception {

		Long estagiarioId2 = estagiarioService.salvar(testUtil.getEstagiarioDto2()).getId();

		Long advogadoId = advogadoService.salvar(testUtil.getAdvogadoDto2()).getId();

		Long professorId = atorRepository.findAll().get(0).getId();

		DemandaDto demandaDto2 = new DemandaDto(null, "Organizar Processos", estagiarioId2, professorId, advogadoId, "Corrigido", "Aguardando Professor", "Aguardando Advogado", "02/11/2025", 13, "Dentro do Prazo");
		demandaService.salvar(demandaDto2);

		mockMvc.perform(get(URI + "/status/Aguardando Aluno?page=0&size=20")
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()").value(2))
				.andExpect(jsonPath("content[0].demanda").value("Organizar Processos"))
				.andExpect(jsonPath("content[0].demandaStatusAluno").value("Aguardando Aluno"));
	}

    @Test
    @Order(4)
    @DisplayName("Deve Mudar Demanda Status Pelo Controller")
    void mudar_demanda_status() throws Exception {

        Long id = demandaRepository.findAll().get(0).getId();
		DemandaStatusDto demandaStatusDto = new DemandaStatusDto("Devolvido", "Null", "Null");

		String jsonRequest = objectMapper.writeValueAsString(demandaStatusDto);

        mockMvc.perform(put(URI + "/" + id + "/update")
                        .header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
                .andExpect(status().isNoContent());

        Demanda demanda = demandaRepository.findById(id).get();

        assertEquals(EtapaDemanda.DEVOLVIDO, demanda.getDemandaStatusAluno());
    }

	@Test
	@DisplayName("Deve Buscar Todas as Demanda Pelo Status No Banco de Dados Pelo Controller")
	void buscar_todas_as_demandas() throws Exception {

		mockMvc.perform(get(URI + "/status/todos?page=0&size=20")
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()").value(2));
	}
	
	@Test
	@DisplayName("Deve Buscar Demanda No Banco de Dados Pelo Controller")
	void buscar_demandas() throws Exception {
		
		mockMvc.perform(get(URI)
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()").value(2));
	}
	
	@Test
	@DisplayName("Deve Buscar Demanda Por Estagiario Id No Banco de Dados Pelo Controller")
	void buscar_demandas_por_estagiarioId() throws Exception {
		
		Long estagiarioId = estagiarioRepository.findAll().get(0).getId();
		
		mockMvc.perform(get(URI + "/estagiario/" + estagiarioId)
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()").value(1))
				.andExpect(jsonPath("content[0].estagiarioNome").value("Pedro Lucas"));
	}

	@Test
	@DisplayName("Deve Buscar Demanda Por Advogado Id No Banco de Dados Pelo Controller")
	void buscar_demandas_por_advogadoId() throws Exception {

		Long advogadoId = advogadoRepository.findAll().get(0).getId();

		mockMvc.perform(get(URI + "/advogado/" + advogadoId)
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()").value(1))
				.andExpect(jsonPath("content[0].advogadoNome").value("Carlos Silva"));
	}

	@Test
	@DisplayName("Deve Buscar Demanda Por Professor Id No Banco de Dados Pelo Controller")
	void buscar_demandas_por_professorId() throws Exception {

		Professor professor = (Professor) atorRepository.findAll().get(0);

		mockMvc.perform(get(URI + "/professor/" + professor.getId())
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()").value(2))
				.andExpect(jsonPath("content[0].professorNome").value("Fabio Junior"));
	}

	@Test
	@DisplayName("Deve Buscar Demanda Status do ADMIN Pelo Controller")
	void buscar_demanda_status_admin() throws Exception {

		mockMvc.perform(get(URI + "/role/admin")
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(EtapaDemanda.values().length)))
				.andExpect(jsonPath("$[0]", equalTo("Corrigido")))
				.andExpect(jsonPath("$[1]", equalTo("Em Correção")))
				.andExpect(jsonPath("$[2]", equalTo("Devolvido")))
				.andExpect(jsonPath("$[3]", equalTo("Dentro do Prazo")))
				.andExpect(jsonPath("$[4]", equalTo("Fora do Prazo")))
				.andExpect(jsonPath("$[5]", equalTo("Recebido")))
				.andExpect(jsonPath("$[6]", equalTo("Protocolado")))
				.andExpect(jsonPath("$[7]", equalTo("Aguardando Professor")));
	}

	@Test
	@DisplayName("Deve Buscar Demanda Status do PROFESSOR Pelo Controller")
	void buscar_demanda_status_professor() throws Exception {

		mockMvc.perform(get(URI + "/role/Professor")
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(5)))
				.andExpect(jsonPath("$[0]", equalTo("Em Correção")))
				.andExpect(jsonPath("$[1]", equalTo("Corrigido")))
				.andExpect(jsonPath("$[2]", equalTo("Devolvido")))
				.andExpect(jsonPath("$[3]", equalTo("Dentro do Prazo")))
				.andExpect(jsonPath("$[4]", equalTo("Fora do Prazo")));
	}

	@Test
	@DisplayName("Deve Buscar Demanda Status do ADVOGADO Pelo Controller")
	void buscar_demanda_status_advogado() throws Exception {

		mockMvc.perform(get(URI + "/role/Advogado")
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0]", equalTo("Em Correção")))
				.andExpect(jsonPath("$[1]", equalTo("Corrigido")))
				.andExpect(jsonPath("$[2]", equalTo("Devolvido")));
	}
}
