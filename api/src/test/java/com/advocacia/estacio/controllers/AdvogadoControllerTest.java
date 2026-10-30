package com.advocacia.estacio.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.advocacia.estacio.modules.usuarios.UsuarioStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.advocacia.estacio.modules.advogados.Advogado;
import com.advocacia.estacio.modules.advogados.AdvogadoRepository;
import com.advocacia.estacio.utils.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdvogadoControllerTest {
	
	@Autowired
	AdvogadoRepository advogadoRepository;
	
	AdvogadoDto advogadoDto;
	
	@Autowired
	TestUtil testUtil;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private static final String URI = "/advogados";
	
	private static String TOKEN = "";
	
	@Test
	@Order(1)
	void preparando_ambiente_de_testes() {
		testUtil.deleteAll();
		
		TOKEN = testUtil.getToken();
	}
	
	@Test
	@Order(2)
	@DisplayName("Deve Salvar Advogado No Banco de Dados Pelo Controller")
	void salvar_advogado() throws Exception {
		
		assertEquals(0, advogadoRepository.count());
		
		advogadoDto = testUtil.getAdvogadoDto();
		
		String jsonRequest = objectMapper.writeValueAsString(advogadoDto);
		
		mockMvc.perform(post(URI + "/")
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nome", equalTo("Carlos Silva")))
				.andExpect(jsonPath("$.email", equalTo("carlos@gmail.com")))
				.andExpect(jsonPath("$.telefone", equalTo("88566519808")))
				.andExpect(jsonPath("$.cidade", equalTo("São Luís")))
				.andExpect(jsonPath("$.bairro", equalTo("Vila Lobão")))
				.andExpect(jsonPath("$.rua", equalTo("rua do passeio")))
				.andExpect(jsonPath("$.numeroDaCasa", equalTo(11)))
				.andExpect(jsonPath("$.cep", equalTo("53022-112")))
				.andExpect(jsonPath("$.usuarioStatus", equalTo("Ativo")));
		
		assertEquals(1, advogadoRepository.count());
	}
	
	@Test
	@Order(3)
	@DisplayName("Deve Atualizar Advogado No Banco de Dados Pelo Controller")
	void atualizar_advogado() throws Exception {
		
		AdvogadoDto advogadoDto = new AdvogadoDto(null, "Carlos Silva Lima", "carlos22@gmail.com",
		"88566519122", "24/08/1993", "São Luís", "Vila dos Nobres",
		"rua do passeio", 11, "53022-112", "Ativo", "1234");
		
		Long id = advogadoRepository.findAll().get(0).getId();
		
		String jsonRequest = objectMapper.writeValueAsString(advogadoDto);
		
		mockMvc.perform(put(URI + "/" + id)
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isNoContent());
		
		
		Advogado advogadoAtualizado = advogadoRepository.findById(id).get();	
		assertNotNull(advogadoAtualizado);
		assertEquals("Carlos Silva Lima", advogadoAtualizado.getNome());
		assertEquals("carlos22@gmail.com", advogadoAtualizado.getEmail());
		assertEquals("Ativo", advogadoAtualizado.getUsuarioAuth().getUsuarioStatus().getDescricao());
		assertEquals("88566519122", advogadoAtualizado.getTelefone());
		assertEquals("1993-08-24", advogadoAtualizado.getDataDeNascimeto().toString());
		assertEquals("Vila dos Nobres", advogadoAtualizado.getEndereco().getBairro());
		assertEquals(UsuarioStatus.ATIVO, advogadoAtualizado.getUsuarioAuth().getUsuarioStatus());
		
		assertEquals(1, advogadoRepository.count());
	}

	@Test
	@DisplayName("Deve Desavitar Advogados Pelo Controller")
	void buscar_desativar_usuarios() throws Exception {

		Advogado advogado = advogadoRepository.findAll().get(0);

		assertEquals(UsuarioStatus.ATIVO, advogado.getUsuarioAuth().getUsuarioStatus());

		List<Long> ids = advogadoRepository.findAll().stream().map(Advogado::getId).toList();

		String jsonRequest = objectMapper.writeValueAsString(new RequestIds(ids));

		mockMvc.perform(patch(URI + "/desativar/usuarios")
				.header("Authorization", "Bearer " + TOKEN)
				.content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		advogado = advogadoRepository.findAll().get(0);

		assertEquals(UsuarioStatus.INATIVO, advogado.getUsuarioAuth().getUsuarioStatus());

	}
	
	@Test
	@DisplayName("Deve Buscar Advogado por Nome Pelo Controller")
	void buscar_advogado_por_nome() throws Exception {
		
		String nome = "il";
		
		mockMvc.perform(get(URI + "/buscar/" + nome)
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content.length()").value(1))
				.andExpect(jsonPath("content[0].nome").value("Carlos Silva Lima"));
	}

	@Test
	@DisplayName("Deve Buscar Todos os Advogados Pelo Controller")
	void buscar_todos_os_Advogados() throws Exception {

		mockMvc.perform(get(URI)
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content.length()").value(1));
	}
	
	@Test
	@DisplayName("Deve buscar Advogado Por Id Pelo Controller")
	void buscar_advogado_por_id() throws Exception {
		
		Long id = advogadoRepository.findAll().get(0).getId();
		
		mockMvc.perform(get(URI + "/" + id)
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome", equalTo("Carlos Silva Lima")))
				.andExpect(jsonPath("$.email", equalTo("carlos22@gmail.com")))
				.andExpect(jsonPath("$.telefone", equalTo("88566519122")))
				.andExpect(jsonPath("$.cidade", equalTo("São Luís")))
				.andExpect(jsonPath("$.bairro", equalTo("Vila dos Nobres")))
				.andExpect(jsonPath("$.rua", equalTo("rua do passeio")))
				.andExpect(jsonPath("$.numeroDaCasa", equalTo(11)))
				.andExpect(jsonPath("$.cep", equalTo("53022-112")))
				.andExpect(jsonPath("$.usuarioStatus", equalTo("Ativo")));
	}

	@Test
	@DisplayName("Deve Buscar Advogado Id por email No Banco de Dados Pelo Controller")
	void buscar_advogado_id_por_email() throws Exception {
		String email = advogadoRepository.findAll().get(0).getEmail();
		Advogado advogado = advogadoRepository.findAll().get(0);

		mockMvc.perform(get(URI + "/buscarId/email/" + email)
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(advogado.getId().intValue()))
				.andExpect(jsonPath("$.nome").value(advogado.getNome()));
	}
}
