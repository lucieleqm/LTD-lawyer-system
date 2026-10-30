package com.advocacia.estacio.services;

import static com.advocacia.estacio.modules.usuarios.UsuarioStatus.ATIVO;
import static com.advocacia.estacio.modules.usuarios.UsuarioStatus.INATIVO;
import static com.advocacia.estacio.utils.Utils.localDateToString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.List;

import com.advocacia.estacio.modules.usuarios.Usuario;
import com.advocacia.estacio.modules.estagiarios.EstagiarioService;
import com.advocacia.estacio.modules.usuarios.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.advocacia.estacio.domain.entities.DesativarAtivarUsuarioPorData;
import com.advocacia.estacio.modules.usuarios.UsuarioRole;
import com.advocacia.estacio.modules.usuarios.UsuarioStatus;
import com.advocacia.estacio.domain.records.AuthenticationDto;
import com.advocacia.estacio.domain.records.LoginResponseDto;
import com.advocacia.estacio.domain.records.RegistroDto;
import com.advocacia.estacio.modules.usuarios.UsuarioRepository;
import com.advocacia.estacio.utils.TestUtil;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioServiceTest {
	
	@Autowired
    UsuarioService usuarioService;
	
	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	DesativarAtivarUsuarioPorDataRepository dataRepository;

	@Autowired
	EstagiarioService estagiarioService;
	
	@Autowired
	TestUtil testUtil;
	
	@Test
	@Order(1)
	void deletando_TodosOsDados_DepoisDostestes() {
		testUtil.deleteAll();	
	}

	@Test
	@Order(2)
	@DisplayName("Deve registrar usuário pelo service")
	void registrar_usuario() {
		
		assertEquals(0, usuarioRepository.count());
		
		RegistroDto registroDto = testUtil.getRegistroDtos().get(0);

		usuarioService.salvar(registroDto);
		
		assertEquals(1, usuarioRepository.count());
		
		Usuario user = usuarioRepository.findAll().get(0);
		
		assertEquals(user.getUsername(), registroDto.login());
		assertEquals(UsuarioRole.ADMIN, user.getRole());
		assertEquals(UsuarioStatus.ATIVO, user.getUsuarioStatus());
	}
	
	@Test
	@Order(3)
	@DisplayName("Deve realizar login e retornar token pelo Service")
	void login_e_retornar_token() {
		AuthenticationDto auth = testUtil.getAuthenticationDto();
		
		LoginResponseDto loginResponse = usuarioService.login(auth);
		
		assertNotNull(loginResponse.token());
		assertEquals(UsuarioRole.ADMIN, loginResponse.role());
	}

	@Test
	@Order(4)
	@DisplayName("Deve Desativar todos os usuários por UsuárioStatus")
	void desativar_usuarios() {
		usuarioService.salvar(testUtil.getRegistroDtos().get(1));
		usuarioService.salvar(testUtil.getRegistroDtos().get(2));

		List<Usuario> usuariosAuth = usuarioRepository.findAll();

		usuarioService.desativarAtivarUsuarios(usuariosAuth, INATIVO);
		assertEquals(INATIVO, usuariosAuth.get(0).getUsuarioStatus());
		assertEquals(INATIVO, usuariosAuth.get(1).getUsuarioStatus());
		assertEquals(INATIVO, usuariosAuth.get(2).getUsuarioStatus());
	}

	@Test
	@Order(5)
	@DisplayName("Deve Ativar todos os usuários por UsuárioStatus")
	void ativar_usuarios() {

		List<Usuario> usuariosAuth = usuarioRepository.findAll();

		usuarioService.ativarUsuarios(usuariosAuth);
		assertEquals(UsuarioStatus.ATIVO, usuariosAuth.get(0).getUsuarioStatus());
		assertEquals(UsuarioStatus.ATIVO, usuariosAuth.get(1).getUsuarioStatus());
		assertEquals(UsuarioStatus.ATIVO, usuariosAuth.get(2).getUsuarioStatus());
	}
	
	@Test
	@DisplayName("Deve Atualizar Senha pelo Service")
	void atualizar_senha() {
		Usuario usuario = usuarioRepository.findAll().get(0);
		String senha = usuario.getPassword();

		usuarioService.atualizarLogin(usuario.getLogin(), usuario.getLogin(), "12345", UsuarioStatus.ATIVO);
		
		String senhaNova = usuarioRepository.findAll().get(0).getPassword();
		
		assertNotEquals(senha, senhaNova);

		usuarioService.login(new AuthenticationDto(usuario.getLogin(), "12345"));
	}
	
	@Test
	@DisplayName("Deve Atualizar Email pelo Service")
	void atualizar_login() {
		Usuario usuario = usuarioRepository.findAll().get(0);
		String loginAntigo = usuario.getLogin();

		usuarioService.atualizarLogin(usuario.getLogin(), "professor_22@gmail.com", "", UsuarioStatus.ATIVO);
		
		String loginNovo = usuarioRepository.findAll().get(0).getLogin();
		
		assertNotEquals(loginAntigo, loginNovo);

		usuarioService.login(new AuthenticationDto("professor_22@gmail.com", "1234"));
	}
	
	@Test
	@DisplayName("Não Deve Atualizar Login pelo Service")
	void nao_atualizar_login() {
		Usuario usuario = usuarioRepository.findAll().get(0);

		usuarioService.atualizarLogin(usuario.getLogin(), usuario.getLogin(), "", UsuarioStatus.ATIVO);
		
		Usuario mesmoUsuario = usuarioRepository.findAll().get(0);
		
		assertEquals(usuario.getLogin(), mesmoUsuario.getLogin());
		assertEquals(usuario.getPassword(), mesmoUsuario.getPassword());
	}

	@Test
	@DisplayName("Deve buscar Usuario Status Pelo Service")
	void buscar_usuario_status() {
		List<UsuarioStatus> usuarioStatus = usuarioService.getUsuarioStatus();

		assertEquals(UsuarioStatus.ATIVO, usuarioStatus.get(0));
		assertEquals(INATIVO, usuarioStatus.get(1));;
	}

//	@Test
//	@DisplayName("Deve definir data de desativacao")
//	void definir_data_de_desativacao() {
//		String data = "25/10/2026";
//		Long id = dataRepository.findAll().get(1).getId();
//		DesativarAtivarUsuarioPorData desativarAtivarUsuarioPorData = this.usuarioAuthService.buscarDesativarUsuarioPorId(id);
//		desativarAtivarUsuarioPorData.setDataDeDesativacao(null);
//		dataRepository.save(desativarAtivarUsuarioPorData);
//		assertNull(desativarAtivarUsuarioPorData.getDataDeDesativacao());
//
//		this.usuarioAuthService.definirDataParaAtivarDesativar(1L, data);
//
//		desativarAtivarUsuarioPorData = this.usuarioAuthService.buscarDesativarUsuarioPorId(1L);
//		LocalDate localDate = localDateToString(data);
//		assertEquals(localDate, desativarAtivarUsuarioPorData.getDataDeDesativacao());
//	}

	@Test
	@DisplayName("Deve desativar usuários por data de desativacao")
	void desativar_usuarios_por_data_de_desativacao() {

		estagiarioService.salvar(testUtil.getEstagiarioDto2());
		LocalDate hoje = LocalDate.now();
		String hojeStr = localDateToString(hoje);

		DesativarAtivarUsuarioPorData desativarUsuario = new DesativarAtivarUsuarioPorData(UsuarioRole.ESTAGIARIO, hoje, INATIVO);
		this.dataRepository.save(desativarUsuario);

		DesativarAtivarUsuarioPorDataDto desativarUsuarioDto = new DesativarAtivarUsuarioPorDataDto(
				desativarUsuario.getTipoUsuario().getRole(), hojeStr,desativarUsuario.getUsuarioStatus());

		List<Usuario> estagiarioAuth = usuarioService.buscarUsuariosAuthPorRole(UsuarioRole.ESTAGIARIO);
		assertEquals(ATIVO, estagiarioAuth.get(0).getUsuarioStatus());

		usuarioService.desativarAtivarUsuariosPorData();

		estagiarioAuth = usuarioService.buscarUsuariosAuthPorRole(UsuarioRole.ESTAGIARIO);
		assertEquals(INATIVO, estagiarioAuth.get(0).getUsuarioStatus());

	}

	@Test
	@DisplayName("Deve definir data para ativar usuários")
	void deve_definir_data_para_ativar_usuarios() {

		DesativarAtivarUsuarioPorData data = dataRepository.findAll().get(0);
		data.setDataDeDesativacao(null);
		data.setUsuarioStatus(ATIVO);
		dataRepository.save(data);

		data = dataRepository.findAll().get(0);

		assertNull(data.getDataDeDesativacao());

		String hoje = localDateToString(LocalDate.now());

		DesativarAtivarUsuarioPorDataDto dto = new DesativarAtivarUsuarioPorDataDto("Estagiário", hoje, UsuarioStatus.ATIVO);

		this.usuarioService.definirDataDeDesativacao(dto);

		data = dataRepository.findAll().get(1);
		assertEquals(data.getDataDeDesativacao(), LocalDate.now());
	}

	@Test
	@DisplayName("Deve definir data para desativar usuários")
	void deve_definir_data_para_desativar_usuarios() {

		dataRepository.save(testUtil.getDataDesativacaoDto());

		String hoje = localDateToString(LocalDate.now());
		DesativarAtivarUsuarioPorData data = dataRepository.findAll().get(0);
		assertNull(data.getDataDeDesativacao());

		DesativarAtivarUsuarioPorDataDto dto = new DesativarAtivarUsuarioPorDataDto("Estagiário", hoje, INATIVO);

		this.usuarioService.definirDataDeDesativacao(dto);

		data = dataRepository.findAll().get(0);
		assertEquals(data.getDataDeDesativacao(), LocalDate.now());
	}

	private List<Usuario> buscarUsuariosAuthPorRole(UsuarioRole usuarioRole) {
		return usuarioRepository.findAll().stream()
				.filter(u -> u.getRole().equals(usuarioRole))
				.toList();
	}
}
