package com.advocacia.estacio.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.advocacia.estacio.modules.assistidos.Assistido;
import com.advocacia.estacio.modules.pessoas.EstadoCivil;
import com.advocacia.estacio.modules.assistidos.AssistidoRepository;
import com.advocacia.estacio.utils.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AssistidoControllerTest {
	
	@Autowired
	AssistidoRepository assistidoRepository;
	
	@Autowired
	TestUtil testUtil;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	AssistidoDto assistidoDto;
	
	private static final String URI = "/assistidos";
	
	private static String TOKEN = "";
	
	@BeforeEach
	void setUp() {
		assistidoDto = testUtil.getAssistidoDto();
	}
	
	@Test
	@Order(1)
	void preparando_ambiente_de_testes() {
		testUtil.deleteAll();
		TOKEN = testUtil.getToken();
	}
	
	@Test
	@Order(2)
	@DisplayName("Deve Salvar Assistido No Banco de Dados Pelo Controller")
	void salvar_assistido() throws Exception {
		
		assertEquals(0, assistidoRepository.count());
		
		String jsonRequest = objectMapper.writeValueAsString(assistidoDto);
		
		mockMvc.perform(post(URI + "/")
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nome", equalTo("Ana Carla")))
				.andExpect(jsonPath("$.matricula", equalTo("20250815")))
				.andExpect(jsonPath("$.telefone", equalTo("86766523354")))
				.andExpect(jsonPath("$.email", equalTo("ana@gmail.com")))
				.andExpect(jsonPath("$.profissao", equalTo("Cientista de Dados")))
				.andExpect(jsonPath("$.nacionalidade", equalTo("brasileiro")))
				.andExpect(jsonPath("$.naturalidade", equalTo("São Luís/MA")))
				.andExpect(jsonPath("$.estadoCivil", equalTo("Solteiro(a)")))
				.andExpect(jsonPath("$.cidade", equalTo("São Luís")))
				.andExpect(jsonPath("$.bairro", equalTo("Vila Palmeira")))
				.andExpect(jsonPath("$.rua", equalTo("rua dos nobres")))
				.andExpect(jsonPath("$.numeroDaCasa", equalTo(12)))
				.andExpect(jsonPath("$.cep", equalTo("43012-232")));
		
		assertEquals(1, assistidoRepository.count());
	}
	
	@Test
	@Order(3)
	@DisplayName("Deve Atualizar Assistido No Banco de Dados Pelo Controller")
	void atualizar_assistido() throws Exception {
		
		AssistidoDto assistidoDto = new AssistidoDto(
				null, "Ana Carla Silva", "20250815", "86766523354",
		"ana22@gmail.com", "Cientista de Dados", "brasileiro", 
		"São Luís/MA", "Casado(a)", "São Luís", "Vila dos Nobres", 
		"rua dos nobres", 12, "43012-232");
		
		Long id = assistidoRepository.findAll().get(0).getId();
		
		String jsonRequest = objectMapper.writeValueAsString(assistidoDto);
		
		mockMvc.perform(put(URI + "/" + id)
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isNoContent());
		
		Assistido assistidoAtualizado = assistidoRepository.findById(id).get();
		assertNotNull(assistidoAtualizado);
		assertEquals("Ana Carla Silva", assistidoAtualizado.getNome());
		assertEquals("20250815", assistidoAtualizado.getMatricula());
		assertEquals("86766523354", assistidoAtualizado.getTelefone());
		assertEquals("ana22@gmail.com", assistidoAtualizado.getEmail());
		assertEquals("Cientista de Dados", assistidoAtualizado.getProfissao());
		assertEquals("brasileiro", assistidoAtualizado.getNacionalidade());
		assertEquals("São Luís/MA", assistidoAtualizado.getNaturalidade());
		assertEquals(EstadoCivil.CASADO, assistidoAtualizado.getEstadoCivil());
		assertEquals("Vila dos Nobres", assistidoAtualizado.getEndereco().getBairro());
		
		assertEquals(1, assistidoRepository.count());
	}
	
	@Test
	@DisplayName("Deve Salvar Assistido Por Id No Banco De Dados Pelo Controller")
	void buscar_por_id() throws Exception {
		
		Long id = assistidoRepository.findAll().get(0).getId();
		
		mockMvc.perform(get(URI + "/" + id)
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome", equalTo("Ana Carla Silva")))
				.andExpect(jsonPath("$.matricula", equalTo("20250815")))
				.andExpect(jsonPath("$.telefone", equalTo("86766523354")))
				.andExpect(jsonPath("$.email", equalTo("ana22@gmail.com")))
				.andExpect(jsonPath("$.profissao", equalTo("Cientista de Dados")))
				.andExpect(jsonPath("$.nacionalidade", equalTo("brasileiro")))
				.andExpect(jsonPath("$.naturalidade", equalTo("São Luís/MA")))
				.andExpect(jsonPath("$.estadoCivil", equalTo("Casado(a)")))
				.andExpect(jsonPath("$.cidade", equalTo("São Luís")))
				.andExpect(jsonPath("$.bairro", equalTo("Vila dos Nobres")))
				.andExpect(jsonPath("$.rua", equalTo("rua dos nobres")))
				.andExpect(jsonPath("$.numeroDaCasa", equalTo(12)))
				.andExpect(jsonPath("$.cep", equalTo("43012-232")));
	}
	
	@Test
	@DisplayName("Deve Buscar Assistido No Banco de Dados Por Nome Pelo Controller")
	void buscar_por_nome() throws Exception {
		
		String nome = "Car";
		
		mockMvc.perform(get(URI + "/buscar/" + nome)
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.content.length()").value(1))
		.andExpect(jsonPath("$.content[0].nome").value("Ana Carla Silva"));
		
	}

	@Test
	@DisplayName("Deve Buscar Todos os Assistidos Pelo Controller")
	void buscar_todos_os_Assistidos() throws Exception {

		mockMvc.perform(get(URI)
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content.length()").value(1));
	}

	@Test
	@DisplayName("Deve Buscar Estados Civis Pelo Controller")
	void buscar_estados_civis() throws Exception {

		mockMvc.perform(get(URI + "/estadosCivis")
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(5)))
				.andExpect(jsonPath("$[0]", equalTo("Solteiro(a)")))
				.andExpect(jsonPath("$[1]", equalTo("Casado(a)")))
				.andExpect(jsonPath("$[2]", equalTo("Divorciado(a)")))
				.andExpect(jsonPath("$[3]", equalTo("Viuvo(a)")))
				.andExpect(jsonPath("$[4]", equalTo("Separado Judicialmente")));
	}
}
