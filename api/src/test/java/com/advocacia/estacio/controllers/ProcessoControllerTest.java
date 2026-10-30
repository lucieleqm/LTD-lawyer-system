package com.advocacia.estacio.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.advocacia.estacio.modules.advogados.Advogado;
import com.advocacia.estacio.modules.estagiarios.Estagiario;
import com.advocacia.estacio.modules.processos.Processo;
import com.advocacia.estacio.modules.estagiarios.EstagiarioRepository;
import com.advocacia.estacio.modules.processos.ProcessoRepository;
import com.advocacia.estacio.modules.advogados.AdvogadoService;
import com.advocacia.estacio.modules.assistidos.AssistidoService;
import com.advocacia.estacio.utils.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProcessoControllerTest {	
	
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
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private static final String URI = "/processos";
	
	private static String TOKEN = "";
	
	@Test
	@Order(1)
	void preparando_ambiente_de_testes() {
		testUtil.deleteAll();
		
		TOKEN = testUtil.getToken();
	}
	
	@Test
	@Order(2)
	@DisplayName("Deve Salvar Processo No Banco de Dados Pelo Controller")
	void salvar_processo() throws Exception {
		
		assertEquals(0, processoRepository.count());
		
		Long assistidoId = assistidoService.salvar(testUtil.getAssistidoDto()).getId();
		Long advogadoId = advogadoService.salvar(testUtil.getAdvogadoDto()).getId();
		Long estagiarioId = estagiarioRepository.save(testUtil.getEstagiario()).getId();
		
		ProcessoRequestDto request = new ProcessoRequestDto(assistidoId, "2543243", "Seguro de Carro", "23423ee23", "Júlio", advogadoId, estagiarioId, "Civil", "Estadual", "25/10/2025");
		
		String jsonRequest = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(post(URI + "/")
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.assistidoId").value(assistidoId))
				.andExpect(jsonPath("$.assunto", equalTo("Seguro de Carro")))
				.andExpect(jsonPath("$.vara", equalTo("23423ee23")))
				.andExpect(jsonPath("$.responsavel", equalTo("Júlio")))
				.andExpect(jsonPath("$.prazoFinal", equalTo("25/10/2025")))
				.andExpect(jsonPath("$.advogadoId", equalTo(advogadoId.intValue())));
		
		assertEquals(1, processoRepository.count());
	}
	
	@Test
	@Order(3)
	@DisplayName("Deve Buscar Processos Por Status No Banco de Dados Pelo Controller")
	void buscar_processos_por_status() throws Exception {
		
		mockMvc.perform(get(URI + "/statusDoProcesso/Tramitando?page=0&size=10")
				.header("Authorization", "Bearer " + TOKEN)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content.length()").value(1))
				.andExpect(jsonPath("$.content[0].id").exists())
				.andExpect(jsonPath("$.content[0].numeroDoProcesso").exists())
				.andExpect(jsonPath("$.content[0].assunto").exists())
				.andExpect(jsonPath("$.content[0].prazoFinal").exists())
				.andExpect(jsonPath("$.content[0].responsavel").exists());
	}
	
	@Test
	@Order(4)
	@DisplayName("Deve Buscar Todos Os Processos No Banco de Dados Pelo Controller")
	void buscar_todos_os_processos() throws Exception {
		
		mockMvc.perform(get(URI + "/statusDoProcesso/todos")
				.header("Authorization", "Bearer " + TOKEN)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	@Order(5)
	@DisplayName("Deve Buscar Processo Por Numero Do Processo No Banco de Dados Pelo Controller")
	void buscar_processo_por_numero_do_processo() throws Exception {
		
		String numeroDoProcesso = processoRepository.findAll().get(0).getNumeroDoProcesso();
		
		mockMvc.perform(get(URI + "/buscar/" + numeroDoProcesso)
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content.length()").value(1))
				.andExpect(jsonPath("content[0].assunto").value("Seguro de Carro"));
	}
	
	@Test
	@Order(6)
	@DisplayName("Deve Atualizar Estagiario No Processo Pelo Controller")
	void atualizar_estagiario_no_processo() throws Exception {
		
		assertEquals(1, processoRepository.count());
		
		Processo processo = processoRepository.findAll().get(0);
		Advogado advogado = advogadoService.buscarPorId(processo.getAdvogado().getId());
		Estagiario estagiario = estagiarioRepository.save(testUtil.getEstagiario2());
		
		ProcessoDto dto = new ProcessoDto(null, processo.getAssistido().getId(), 
				"23232323", "32323232", "Seguro de celular", "132132", "11/12/2025", advogado.getNome(), 
				advogado.getId(), estagiario.getId(), "Previdenciário", "Federal", 
				"Suspenso", null);
		
		String jsonRequest = objectMapper.writeValueAsString(dto);
		
		mockMvc.perform(put(URI + "/" + processo.getId())
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isNoContent());
		
		assertEquals(1, processoRepository.count());
	}

	@Test
	@DisplayName("Deve Buscar Áreas do Direito Civis Pelo Controller")
	void buscar_areas_do_direito() throws Exception {

		mockMvc.perform(get(URI + "/areasDoDireito")
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0]", equalTo("Civil")))
				.andExpect(jsonPath("$[1]", equalTo("Trabalho")))
				.andExpect(jsonPath("$[2]", equalTo("Previdenciário")));
	}

	@Test
	@DisplayName("Deve Buscar Processo Status Pelo Controller")
	void buscar_processo_status() throws Exception {

		mockMvc.perform(get(URI + "/processoStatus")
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0]", equalTo("Tramitando")))
				.andExpect(jsonPath("$[1]", equalTo("Suspenso")))
				.andExpect(jsonPath("$[2]", equalTo("Arquivado")));
	}

	@Test
	@DisplayName("Deve Buscar Tribunais Civis Pelo Controller")
	void buscar_tribunais() throws Exception {

		mockMvc.perform(get(URI + "/tribunais")
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0]", equalTo("Estadual")))
				.andExpect(jsonPath("$[1]", equalTo("Federal")))
				.andExpect(jsonPath("$[2]", equalTo("Trabalho")));
	}
}
