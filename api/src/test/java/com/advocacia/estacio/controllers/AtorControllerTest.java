package com.advocacia.estacio.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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

import com.advocacia.estacio.domain.dto.AtorDto;
import com.advocacia.estacio.domain.entities.Ator;
import com.advocacia.estacio.modules.usuarios.UsuarioStatus;
import com.advocacia.estacio.repositories.AtorRepository;
import com.advocacia.estacio.utils.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AtorControllerTest {
	
	@Autowired
	AtorRepository atorRepository;
	
	@Autowired
	TestUtil testUtil;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private static final String URI = "/atores";
	
	private static String TOKEN = "";
	
	@Test
	@Order(1)
	void preparando_ambiente_de_testes() {
		testUtil.deleteAll();
		
		TOKEN = testUtil.getToken();
	}
	
	@Test
	@Order(2)
	@DisplayName("Deve Salvar Coordenador No Banco de Dados Pelo Controller")
	void salvar_coordenador() throws Exception {
		
		assertEquals(0, atorRepository.count());
		
		AtorDto atorDto = testUtil.getAtores().get(0);
		
		String jsonRequest = objectMapper.writeValueAsString(atorDto);
		
		mockMvc.perform(post(URI + "/")
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nome", equalTo("Roberto Carlos")))
				.andExpect(jsonPath("$.email", equalTo("roberto@gmail.com")))
				.andExpect(jsonPath("$.tipoAtor", equalTo("Coordenador do curso")))
				.andExpect(jsonPath("$.usuarioStatus", equalTo("Ativo")));
		
		assertEquals(1, atorRepository.count());
	}
	
	@Test
	@Order(3)
	@DisplayName("Deve Salvar Secretário No Banco de Dados Pelo Controller")
	void salvar_secretario() throws Exception {
		
		AtorDto atorDto = testUtil.getAtores().get(1);
		
		String jsonRequest = objectMapper.writeValueAsString(atorDto);
		
		mockMvc.perform(post(URI + "/")
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nome", equalTo("José Augusto")))
				.andExpect(jsonPath("$.email", equalTo("jose@gmail.com")))
				.andExpect(jsonPath("$.tipoAtor", equalTo("Secretário")))
				.andExpect(jsonPath("$.usuarioStatus", equalTo("Ativo")));
		
		assertEquals(2, atorRepository.count());
	}
	
	@Test
	@Order(4)
	@DisplayName("Deve Salvar Professor No Banco de Dados Pelo Controller")
	void salvar_professor() throws Exception {
		
		AtorDto atorDto = testUtil.getAtores().get(2);
		
		String jsonRequest = objectMapper.writeValueAsString(atorDto);
		
		mockMvc.perform(post(URI + "/")
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nome", equalTo("Fabio Junior")))
				.andExpect(jsonPath("$.email", equalTo("fabio@gmail.com")))
				.andExpect(jsonPath("$.tipoAtor", equalTo("Professor")))
				.andExpect(jsonPath("$.usuarioStatus", equalTo("Ativo")));
		
		assertEquals(3, atorRepository.count());
	}
	
	@Test
	@Order(5)
	@DisplayName("Deve Atualizar Coordenador No Banco de Dados Pelo Controller")
	void atualizar_coordenador() throws Exception {
		
		AtorDto atorDto = new AtorDto(
				null, "Roberto Carlos Silva", "roberto22@gmail.com", 
				"Coordenador do curso", "Ativo", "1234");
		
		Long id = atorRepository.findAll().get(0).getId();
		
		String jsonRequest = objectMapper.writeValueAsString(atorDto);
		
		mockMvc.perform(put(URI + "/" + id)
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isNoContent());
		
		Ator atorAtualizado = atorRepository.findById(id).get();
		assertNotNull(atorAtualizado);
		assertEquals("roberto22@gmail.com", atorAtualizado.getUsuarioAuth().getLogin());
		assertEquals("Roberto Carlos Silva", atorAtualizado.getNome());
		assertEquals("roberto22@gmail.com", atorAtualizado.getEmail());
		assertEquals("Coordenador do curso", atorAtualizado.getTipoDoAtor().getTipo());
		assertEquals("Ativo", atorAtualizado.getUsuarioAuth().getUsuarioStatus().getDescricao());
	}

	@Test
	@DisplayName("Deve Buscar Somente Coordenador Pelo Controller")
	void buscar_coordenadores() throws Exception {

		mockMvc.perform(get(URI + "/tipo/Coordenador do curso")
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()", equalTo(1)))
				.andExpect(jsonPath("content[0].nome").value("Roberto Carlos Silva"));
	}

	@Test
	@DisplayName("Deve Buscar Somente Secretário Pelo Controller")
	void buscar_secretarios() throws Exception {

		mockMvc.perform(get(URI + "/tipo/Secretário")
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()", equalTo(1)))
				.andExpect(jsonPath("content[0].nome").value("José Augusto"));
	}

	@Test
	@DisplayName("Deve Buscar Somente Professor Pelo Controller")
	void buscar_professor() throws Exception {


		mockMvc.perform(get(URI + "/tipo/Professor")
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()", equalTo(1)))
				.andExpect(jsonPath("content[0].nome").value("Fabio Junior"));
	}
	
	@Test
	@DisplayName("Deve Buscar Coordenador por Id No Banco De Dados Pelo Service")
	void buscar_coordenador_por_id() throws Exception {
		
		Long id = atorRepository.findAll().get(0).getId();
		
		mockMvc.perform(get(URI + "/" + id)
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome", equalTo("Roberto Carlos Silva")))
				.andExpect(jsonPath("$.email", equalTo("roberto22@gmail.com")))
				.andExpect(jsonPath("$.tipoAtor", equalTo("Coordenador do curso")))
				.andExpect(jsonPath("$.usuarioStatus", equalTo("Ativo")));
	}

	@Test
	@DisplayName("Deve Buscar Tipo de Atores Pelo Controller")
	void buscar_tipo_atores() throws Exception {

		mockMvc.perform(get(URI + "/tipoAtores")
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0]", equalTo("Coordenador do curso")))
				.andExpect(jsonPath("$[1]", equalTo("Secretário")))
				.andExpect(jsonPath("$[2]", equalTo("Professor")));
	}

	@Test
	@DisplayName("Deve Desavitar Atores Pelo Controller")
	void buscar_desativar_usuarios() throws Exception {

		List<Ator> atores = atorRepository.findAll();

		List<Long> ids = atorRepository.findAll().stream().map(Ator::getId).toList();

		assertEquals(UsuarioStatus.ATIVO, atores.get(0).getUsuarioAuth().getUsuarioStatus());
		assertEquals(UsuarioStatus.ATIVO, atores.get(1).getUsuarioAuth().getUsuarioStatus());
		assertEquals(UsuarioStatus.ATIVO, atores.get(2).getUsuarioAuth().getUsuarioStatus());

		String jsonRequest = objectMapper.writeValueAsString(new RequestIds(ids));

		mockMvc.perform(patch(URI + "/desativar/usuarios")
						.header("Authorization", "Bearer " + TOKEN)
						.content(jsonRequest)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		atores = atorRepository.findAll();

		assertEquals(UsuarioStatus.INATIVO, atores.get(0).getUsuarioAuth().getUsuarioStatus());
		assertEquals(UsuarioStatus.INATIVO, atores.get(1).getUsuarioAuth().getUsuarioStatus());
		assertEquals(UsuarioStatus.INATIVO, atores.get(2).getUsuarioAuth().getUsuarioStatus());
	}

	@Test
	@DisplayName("Deve Buscar Professor por Nome Pelo Controller")
	void buscar_professor_pelo_nome_pelo_controller() throws Exception {

		String nome = "fa";
		String tipoDoAtor = "Professor";

		mockMvc.perform(get(URI + "/buscar/" + tipoDoAtor + "/" + nome)
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()", equalTo(1)))
				.andExpect(jsonPath("content[0].nome").value("Fabio Junior"));
	}
}
