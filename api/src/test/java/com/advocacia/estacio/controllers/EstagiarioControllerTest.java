package com.advocacia.estacio.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.advocacia.estacio.modules.usuarios.Usuario;
import com.advocacia.estacio.modules.usuarios.UsuarioStatus;
import com.advocacia.estacio.modules.usuarios.UsuarioRepository;
import com.advocacia.estacio.modules.estagiarios.EstagiarioService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.advocacia.estacio.modules.estagiarios.Estagiario;
import com.advocacia.estacio.modules.estagiarios.PeriodoEstagio;
import com.advocacia.estacio.modules.estagiarios.EstagiarioRepository;
import com.advocacia.estacio.utils.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EstagiarioControllerTest {
	
	@Autowired
	EstagiarioRepository estagiarioRepository;

	@Autowired
	EstagiarioService estagiarioService;

	@Autowired
    UsuarioRepository usuarioRepository;
	
	@Autowired
	TestUtil testUtil;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private static final String URI = "/estagiarios";
	
	private static String TOKEN = "";
	
	@Test
	@Order(1)
	void preparando_ambiente_de_testes() {
		testUtil.deleteAll();
		
		TOKEN = testUtil.getToken();
	}
	
	@Test
	@Order(2)
	@DisplayName("Deve Salvar Estagiario No Banco de Dados Pelo Controller")
	void salver_estagiario() throws Exception {
		
		assertEquals(0, estagiarioRepository.count());
		
		EstagiarioDto dto = testUtil.getEstagiarioDto();
		
		String jsonRequest = objectMapper.writeValueAsString(dto);
		
		mockMvc.perform(post(URI + "/")
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nome", equalTo("Pedro Lucas")))
				.andExpect(jsonPath("$.email", equalTo("pedro@gmail.com")))
				.andExpect(jsonPath("$.matricula", equalTo("20251208")))
				.andExpect(jsonPath("$.periodo", equalTo("Estágio I")))
				.andExpect(jsonPath("$.usuarioStatus", equalTo("Ativo")));
		
		assertEquals(1, estagiarioRepository.count());
	}
	
	@Test
	@Order(3)
	@DisplayName("Deve Atualizar Estagiario No Banco de Dados Pelo Controller")
	void atualizar_estagiario() throws Exception {
		
		Long id = estagiarioRepository.findAll().get(0).getId();
		
		EstagiarioDto estagiario = new EstagiarioDto(null,
		"Pedro Silva Lucas", "pedro22@gmail.com", "92921421224", "20251208",
		"Estágio II", "Ativo", "12345");
		
		String jsonRequest = objectMapper.writeValueAsString(estagiario);
		
		mockMvc.perform(put(URI + "/" + id)
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isNoContent());
		
		Estagiario estagiarioAtualizado = estagiarioRepository.findById(id).get();
		
		assertEquals("Pedro Silva Lucas", estagiarioAtualizado.getNome());
		assertEquals("pedro22@gmail.com", estagiarioAtualizado.getEmail());
		assertEquals("pedro22@gmail.com", estagiarioAtualizado.getUsuarioAuth().getLogin());
		assertEquals("20251208", estagiarioAtualizado.getMatricula());
		assertEquals(PeriodoEstagio.ESTAGIO_II, estagiarioAtualizado.getPeriodo());
		assertEquals(UsuarioStatus.ATIVO, estagiarioAtualizado.getUsuarioAuth().getUsuarioStatus());
		
		assertEquals(1, estagiarioRepository.count());
	}
	
	@Test
	@DisplayName("Deve Buscar Estagiario Por Nome No Banco de Dados Pelo Controller")
	void salvar_estagiario() throws Exception {
		var estagiario = estagiarioRepository.findAll().get(0);
		String nome = estagiario.getNome();
		
		mockMvc.perform(get(URI + "/buscar/" + nome)
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content.length()").value(1))
				.andExpect(jsonPath("$.content[0].nome").value("Pedro Silva Lucas"));
		
	}

	@Test
	@DisplayName("Deve Buscar Estagiario Id por email No Banco de Dados Pelo Controller")
	void buscar_estagiario_id_por_email() throws Exception {
		var email = estagiarioRepository.findAll().get(0).getEmail();
		Estagiario estagiario = estagiarioRepository.findAll().get(0);

		mockMvc.perform(get(URI + "/buscarId/email/" + email)
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(estagiario.getId().intValue()))
				.andExpect(jsonPath("$.nome").value(estagiario.getNome()));
	}
	
	@Test
	@DisplayName("Deve Buscar Estagiario por Id No Banco de Dados Pelo Controller")
	void buscar_estagiarioDto_por_id() throws Exception {
		Long id = estagiarioRepository.findAll().get(0).getId();
		Estagiario estagiario = estagiarioRepository.findAll().get(0);

		mockMvc.perform(get(URI + "/" + id)
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(estagiario.getId().intValue()))
				.andExpect(jsonPath("$.nome").value(estagiario.getNome()))
				.andExpect(jsonPath("$.matricula").value(estagiario.getMatricula()))
				.andExpect(jsonPath("$.periodo").value(estagiario.getPeriodo().getTipo()))
				.andExpect(jsonPath("$.usuarioStatus").value(estagiario.getUsuarioAuth().getUsuarioStatus().getDescricao()));
	}

	@Test
	@DisplayName("Deve Buscar Todos os Estagiarios Pelo Controller")
	void buscar_todos_os_Estagiarios() throws Exception {

		mockMvc.perform(get(URI)
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content.length()").value(1))
				.andExpect(jsonPath("$.content[0].nome").value("Pedro Silva Lucas"))
				.andExpect(jsonPath("$.content[0].email").value("pedro22@gmail.com"))
				.andExpect(jsonPath("$.content[0].telefone").value("92921421224"))
				.andExpect(jsonPath("$.content[0].matricula").value("20251208"))
				.andExpect(jsonPath("$.content[0].periodo").value("Estágio II"))
				.andExpect(jsonPath("$.content[0].usuarioStatus").value("Inativo"));
	}

	@Test
	@DisplayName("Deve buscar Todos os Períodos Pelo Controller")
	void buscar_periodos() throws Exception {

		mockMvc.perform(get(URI + "/periodos")
						.header("Authorization", "Bearer " + TOKEN)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(4)))
				.andExpect(jsonPath("$[0]", equalTo("Estágio I")))
				.andExpect(jsonPath("$[1]", equalTo("Estágio II")))
				.andExpect(jsonPath("$[2]", equalTo("Estágio III")))
				.andExpect(jsonPath("$[3]", equalTo("Estágio IV")));
	}

	@Test
	@DisplayName("Deve Desavitar Estagiários Pelo Controller")
	void desativar_usuarios() throws Exception {

		Estagiario estagiario = estagiarioRepository.findAll().get(0);
		Usuario auth = estagiario.getUsuarioAuth();
		auth.setUsuarioStatus(UsuarioStatus.ATIVO);
		usuarioRepository.save(auth);

		assertEquals(UsuarioStatus.ATIVO, estagiario.getUsuarioAuth().getUsuarioStatus());

		List<Long> ids = estagiarioRepository.findAll().stream().map(Estagiario::getId).toList();

		String jsonRequest = objectMapper.writeValueAsString(new RequestIds(ids));

		mockMvc.perform(patch(URI + "/desativar/usuarios")
						.header("Authorization", "Bearer " + TOKEN)
						.content(jsonRequest)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		estagiario = estagiarioRepository.findAll().get(0);

		assertEquals(UsuarioStatus.INATIVO, estagiario.getUsuarioAuth().getUsuarioStatus());

	}

//	@Test
//	@DisplayName("Deve Desavitar Estagiários por Data Pelo Controller")
//	void desativar_usuarios_por_data() throws Exception {
//
//		UsuarioAuth auth = this.estagiarioRepository.findAll().get(0).getUsuarioAuth();
//		auth.setUsuarioStatus(UsuarioStatus.ATIVO);
//
//		this.usuarioAuthRepository.save(auth);
//
//		List<Estagiario> estagiarios = this.estagiarioRepository.findAll();
//
//		assertEquals(UsuarioStatus.ATIVO, estagiarios.get(0).getUsuarioAuth().getUsuarioStatus());
//
//		String dataHoje =  Utils.localDateToString(LocalDate.now());
//
//		DesativarAtivarUsuarioPorDataDto dto = new DesativarAtivarUsuarioPorDataDto("Estagiário", dataHoje, UsuarioStatus.INATIVO);
//
//		String jsonRequest = objectMapper.writeValueAsString(dto);
//
//		mockMvc.perform(put(URI + "/Inativo/estagiarios")
//						.header("Authorization", "Bearer " + TOKEN)
//						.content(jsonRequest)
//						.contentType(MediaType.APPLICATION_JSON))
//						.andExpect(status().isNoContent());
//
//		estagiarios = this.estagiarioRepository.findAll();
//
//		assertEquals(UsuarioStatus.INATIVO, estagiarios.get(0).getUsuarioAuth().getUsuarioStatus());
//	}

//	@Test
//	@DisplayName("Deve Definir Data para ativar Estagiários Pelo Controller")
//	void definir_data_para_ativar() throws Exception {
//
//		String dataHoje =  Utils.localDateToString(LocalDate.now());
//
//		DesativarAtivarUsuarioPorDataDto dto = new DesativarAtivarUsuarioPorDataDto("Estagiário", dataHoje, UsuarioStatus.ATIVO);
//		Long id = dataRepository.findAll().get(0).getId();
//
//		String jsonRequest = objectMapper.writeValueAsString(dto);
//
//		mockMvc.perform(put(URI + "/data/" + id + "/ativarDesativar/")
//						.header("Authorization", "Bearer " + TOKEN)
//						.content(jsonRequest)
//						.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isNoContent());
//
//		LocalDate data = dataRepository.findAll().get(0).getDataDeDesativacao();
//
//		assertEquals(Utils.localDateToString(dataHoje), data);
//	}

//	@Test
//	@DisplayName("Deve Avitar Estagiários por Data Pelo Controller")
//	void ativar_usuarios_por_data() throws Exception {
//
//		UsuarioAuth auth = this.estagiarioRepository.findAll().get(0).getUsuarioAuth();
//		auth.setUsuarioStatus(UsuarioStatus.INATIVO);
//
//		this.usuarioAuthRepository.save(auth);
//
//		List<Estagiario> estagiarios = this.estagiarioRepository.findAll();
//
//		assertEquals(UsuarioStatus.INATIVO, estagiarios.get(0).getUsuarioAuth().getUsuarioStatus());
//
//		String dataHoje =  Utils.localDateToString(LocalDate.now());
//
//		DesativarAtivarUsuarioPorDataDto dto = new DesativarAtivarUsuarioPorDataDto("Estagiário", dataHoje, UsuarioStatus.ATIVO);
//
//		String jsonRequest = objectMapper.writeValueAsString(dto);
//
//		mockMvc.perform(put(URI + "/Ativo/estagiarios")
//						.header("Authorization", "Bearer " + TOKEN)
//						.content(jsonRequest)
//						.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isNoContent());
//
//		estagiarios = this.estagiarioRepository.findAll();
//
//		assertEquals(UsuarioStatus.ATIVO, estagiarios.get(0).getUsuarioAuth().getUsuarioStatus());
//	}

//	@Test
//	@DisplayName("Deve Definir data e desativar Estagiários Controller")
//	void definiar_data_e_desativar_usuario() throws Exception {
//
//		UsuarioAuth auth = this.estagiarioRepository.findAll().get(0).getUsuarioAuth();
//		auth.setUsuarioStatus(UsuarioStatus.ATIVO);
//
//		this.usuarioAuthRepository.save(auth);
//
//		Estagiario estagiario = this.estagiarioRepository.findAll().get(0);
//
//		assertEquals(UsuarioStatus.ATIVO, estagiario.getUsuarioAuth().getUsuarioStatus());
//
//		String dataHoje =  Utils.localDateToString(LocalDate.now());
//
//		DesativarAtivarUsuarioPorDataDto dto = new DesativarAtivarUsuarioPorDataDto("Estagiário", dataHoje, UsuarioStatus.INATIVO);
//		String jsonRequest = objectMapper.writeValueAsString(dto);
//
//		Long id = dataRepository.findAll().get(0).getId();
//
//		this.estagiarioService.definirDataDeDesativacao(id, dto.getDataDeDesativacao());
//
//		mockMvc.perform(put(URI + "/Inativo/estagiarios")
//						.header("Authorization", "Bearer " + TOKEN)
//						.content(jsonRequest)
//						.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isNoContent());
//
//		estagiario = this.estagiarioRepository.findAll().get(0);
//
//		assertEquals(UsuarioStatus.INATIVO, estagiario.getUsuarioAuth().getUsuarioStatus());
//	}

//	@DisplayName("Deve Definir data e ativar Estagiários Controller")
//	void definiar_data_e_ativar_usuario() throws Exception {
//
//		UsuarioAuth auth = this.estagiarioRepository.findAll().get(0).getUsuarioAuth();
//		auth.setUsuarioStatus(UsuarioStatus.INATIVO);
//
//		this.usuarioAuthRepository.save(auth);
//
//		Estagiario estagiario = this.estagiarioRepository.findAll().get(0);
//
//		assertEquals(UsuarioStatus.INATIVO, estagiario.getUsuarioAuth().getUsuarioStatus());
//
//		String dataHoje =  Utils.localDateToString(LocalDate.now());
//
//		DesativarAtivarUsuarioPorDataDto dto = new DesativarAtivarUsuarioPorDataDto("Estagiário", dataHoje, UsuarioStatus.ATIVO);
//		String jsonRequest = objectMapper.writeValueAsString(dto);
//
//		Long id = dataRepository.findAll().get(0).getId();
//
//		this.estagiarioService.definirDataDeDesativacao(id, dto.getDataDeDesativacao());
//
//		mockMvc.perform(put(URI + "/Ativo/estagiarios")
//						.header("Authorization", "Bearer " + TOKEN)
//						.content(jsonRequest)
//						.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isNoContent());
//
//		estagiario = this.estagiarioRepository.findAll().get(0);
//
//		assertEquals(UsuarioStatus.ATIVO, estagiario.getUsuarioAuth().getUsuarioStatus());
//	}
}
