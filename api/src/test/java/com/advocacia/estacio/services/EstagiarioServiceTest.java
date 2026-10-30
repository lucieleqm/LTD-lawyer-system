package com.advocacia.estacio.services;

import static com.advocacia.estacio.utils.Utils.localDateToString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.advocacia.estacio.modules.usuarios.Usuario;
import com.advocacia.estacio.modules.usuarios.UsuarioStatus;
import com.advocacia.estacio.modules.estagiarios.EstagiarioService;
import com.advocacia.estacio.utils.Utils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.advocacia.estacio.modules.estagiarios.Estagiario;
import com.advocacia.estacio.modules.estagiarios.PeriodoEstagio;
import com.advocacia.estacio.modules.usuarios.UsuarioRole;
import com.advocacia.estacio.domain.records.EntidadeMinDto;
import com.advocacia.estacio.modules.estagiarios.EstagiarioRepository;
import com.advocacia.estacio.modules.usuarios.UsuarioRepository;
import com.advocacia.estacio.utils.TestUtil;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EstagiarioServiceTest {
	
	@Autowired
	EstagiarioService estagiarioService;
	
	@Autowired
	EstagiarioRepository estagiarioRepository;
	
	@Autowired
    UsuarioRepository usuarioRepository;
	
	@Autowired
	TestUtil testUtil;
	
	@Test
	@Order(1)
	void deveDeletar_TodosOsDados_AntesDostestes() {
		testUtil.deleteAll();
	}

	@Test
	@Order(2)
	@DisplayName("Deve Salvar Estagiario No Banco de Dados Pelo Service")
	void salvar_estagiario() {
		assertEquals(0, estagiarioRepository.count());
		assertEquals(0, usuarioRepository.count());
		
		Estagiario estagiarioSalvo = estagiarioService.salvar(testUtil.getEstagiarioDto());
		
		assertEquals("Pedro Lucas", estagiarioSalvo.getNome());
		assertEquals("pedro@gmail.com", estagiarioSalvo.getEmail());
		assertEquals("20251208", estagiarioSalvo.getMatricula());
		assertEquals(PeriodoEstagio.ESTAGIO_I, estagiarioSalvo.getPeriodo());
		assertEquals(UsuarioStatus.ATIVO, estagiarioSalvo.getUsuarioAuth().getUsuarioStatus());
		
		Usuario userAuth = (Usuario) usuarioRepository.findByLogin(estagiarioSalvo.getEmail());
		
		assertEquals(1, estagiarioRepository.count());
		assertEquals(1, usuarioRepository.count());
		assertEquals(UsuarioRole.ESTAGIARIO, userAuth.getRole());
	}
	
	@Test
	@Order(3)
	@DisplayName("Deve Atualziar Estagiario No Banco de Dados Pelo Service")
	void atualizar_estagiario() {
		
		Long id = estagiarioRepository.findAll().get(0).getId();
		
		EstagiarioDto estagiario = new EstagiarioDto(null,
		"Pedro Silva Lucas", "pedro22@gmail.com", "92921421224","20251208",
		"Estágio II", "Inativo", "12345");
		
		Estagiario estagiarioAtualizado = estagiarioService.atualizar(id, estagiario);
		
		assertEquals("Pedro Silva Lucas", estagiarioAtualizado.getNome());
		assertEquals("pedro22@gmail.com", estagiarioAtualizado.getEmail());
		assertEquals("20251208", estagiarioAtualizado.getMatricula());
		assertEquals(PeriodoEstagio.ESTAGIO_II, estagiarioAtualizado.getPeriodo());
		assertEquals(UsuarioStatus.INATIVO, estagiarioAtualizado.getUsuarioAuth().getUsuarioStatus());
		
		Usuario userAuth = (Usuario)
				usuarioRepository.findByLogin(estagiarioAtualizado.getEmail());
		
		assertEquals("pedro22@gmail.com", userAuth.getLogin());
		
		assertEquals(1, estagiarioRepository.count());
	}

	@Test
	@Order(4)
	@DisplayName("Deve Buscar Usuários Auth por Lista de ids")
	void buscar_usuariosAuth() {

		estagiarioService.salvar(testUtil.getEstagiarioDto2());

		List<Long> ids = estagiarioRepository.findAll().stream().map(Estagiario::getId).toList();

		List<Usuario> usuariosAuth = estagiarioService.buscarUsuariosAuthPorId(ids);

		assertEquals(2, usuariosAuth.size());
		assertEquals("pedro22@gmail.com", usuariosAuth.get(0).getLogin());
		assertEquals("joao@gmail.com", usuariosAuth.get(1).getLogin());
	}

	@Test
	@DisplayName("Deve Buscar Estagiario Por Id No Banco de Dados Pelo Service")
	void buscar_estagiario_por_id() {

		Long estagiarioId = estagiarioRepository.findAll().get(0).getId();
		Estagiario estagiario = estagiarioService.buscarPorId(estagiarioId);

		assertEquals("Pedro Silva Lucas", estagiario.getNome());
		assertEquals("pedro22@gmail.com", estagiario.getEmail());
		assertEquals("20251208", estagiario.getMatricula());
		assertEquals(PeriodoEstagio.ESTAGIO_II, estagiario.getPeriodo());
		assertEquals(UsuarioStatus.INATIVO, estagiario.getUsuarioAuth().getUsuarioStatus());
	}

	@Test
	@DisplayName("Deve Buscar Estagiario Por Nome No Banco de Dados Pelo Service")
	void buscar_estagiario_por_nome() {

		Page<Estagiario> estagiarios = estagiarioService.buscarEstagiario("dro", 0, 20);

		assertEquals("Pedro Silva Lucas", estagiarios.getContent().get(0).getNome());
		assertEquals("pedro22@gmail.com", estagiarios.getContent().get(0).getEmail());
		assertEquals("92921421224", estagiarios.getContent().get(0).getTelefone());
		assertEquals("20251208", estagiarios.getContent().get(0).getMatricula());
		assertEquals(PeriodoEstagio.ESTAGIO_II, estagiarios.getContent().get(0).getPeriodo());
		assertEquals(UsuarioStatus.INATIVO, estagiarios.getContent().get(0).getUsuarioAuth().getUsuarioStatus());
	}

	@Test
	@DisplayName("Deve Buscar Estagiario Id Por Email No Banco de Dados Pelo Service")
	void deve_buscar_Estagiario_id_pelo_email_PeloService() {

		Estagiario estagiario = estagiarioRepository.findAll().get(0);
		EntidadeMinDto dto = estagiarioService.buscarIdPorEmail("pedro22@gmail.com");

		assertEquals(estagiario.getId(), dto.id());
		assertEquals(estagiario.getNome(), dto.nome());
	}

	@Test
	@DisplayName("Deve buscar Todos os Estagiarios Pelo Service")
	void buscar_todos() {

		Page<Estagiario> pages = estagiarioService.buscarTodos(0, 20);

		assertFalse(pages.isEmpty());
		assertEquals(2, pages.getContent().size());
		assertEquals("Pedro Silva Lucas", pages.getContent().get(1).getNome());
		assertEquals("pedro22@gmail.com", pages.getContent().get(1).getEmail());
		assertEquals("92921421224", pages.getContent().get(1).getTelefone());
		assertEquals("20251208", pages.getContent().get(1).getMatricula());
		assertEquals(PeriodoEstagio.ESTAGIO_II, pages.getContent().get(1).getPeriodo());
		assertEquals(UsuarioStatus.INATIVO, pages.getContent().get(1).getUsuarioAuth().getUsuarioStatus());

		assertEquals("João Miguel", pages.getContent().get(0).getNome());
		assertEquals("joao@gmail.com", pages.getContent().get(0).getEmail());
		assertEquals("92921421224", pages.getContent().get(0).getTelefone());
		assertEquals("20251208", pages.getContent().get(0).getMatricula());
		assertEquals(PeriodoEstagio.ESTAGIO_II, pages.getContent().get(0).getPeriodo());
		assertEquals(UsuarioStatus.ATIVO, pages.getContent().get(0).getUsuarioAuth().getUsuarioStatus());
	}

	@Test
	@DisplayName("Deve buscar Todos os Períodos Pelo Service")
	void buscar_periodos() {
		List<PeriodoEstagio> periodos = estagiarioService.getPeriodos();

		assertEquals(PeriodoEstagio.ESTAGIO_I, periodos.get(0));
		assertEquals(PeriodoEstagio.ESTAGIO_II, periodos.get(1));
		assertEquals(PeriodoEstagio.ESTAGIO_III, periodos.get(2));
		assertEquals(PeriodoEstagio.ESTAGIO_IV, periodos.get(3));
	}

//	@Test
//	@DisplayName("Deve Desativar Estagiários por Data Pelo Service")
//	void desativar_estagiarios_por_data() {
//		List<UsuarioAuth> auths = this.estagiarioRepository.findAll().stream().map(Estagiario::getUsuarioAuth).toList();
//		auths = auths.stream().map(a -> {
//			a.setUsuarioStatus(UsuarioStatus.ATIVO);
//			return a;
//		}).toList();
//
//		this.usuarioAuthRepository.saveAll(auths);
//
//		List<Estagiario> estagiarios = this.estagiarioRepository.findAll();
//
//		assertEquals(UsuarioStatus.ATIVO, estagiarios.get(0).getUsuarioAuth().getUsuarioStatus());
//		assertEquals(UsuarioStatus.ATIVO, estagiarios.get(1).getUsuarioAuth().getUsuarioStatus());
//
//		String dataHoje =  Utils.localDateToString(LocalDate.now());
//
//		DesativarAtivarUsuarioPorDataDto dto = new DesativarAtivarUsuarioPorDataDto("Estagiário", dataHoje, UsuarioStatus.INATIVO);
//
//		this.estagiarioService.desativarEstagiariosPorData(dto, "Inativo");
//
//		estagiarios = this.estagiarioRepository.findAll();
//
//		assertEquals(UsuarioStatus.INATIVO, estagiarios.get(0).getUsuarioAuth().getUsuarioStatus());
//		assertEquals(UsuarioStatus.INATIVO, estagiarios.get(1).getUsuarioAuth().getUsuarioStatus());
//	}

//	@Test
//	@DisplayName("Deve Ativar Estagiários por Data Pelo Service")
//	void ativar_estagiarios_por_data() {
//		List<UsuarioAuth> auths = this.estagiarioRepository.findAll().stream().map(Estagiario::getUsuarioAuth).toList();
//		auths = auths.stream().map(a -> {
//			a.setUsuarioStatus(UsuarioStatus.INATIVO);
//			return a;
//		}).toList();
//
//		this.usuarioAuthRepository.saveAll(auths);
//
//		List<Estagiario> estagiarios = this.estagiarioRepository.findAll();
//
//		assertEquals(UsuarioStatus.INATIVO, estagiarios.get(0).getUsuarioAuth().getUsuarioStatus());
//		assertEquals(UsuarioStatus.INATIVO, estagiarios.get(1).getUsuarioAuth().getUsuarioStatus());
//
//		String dataHoje =  Utils.localDateToString(LocalDate.now());
//
//		DesativarAtivarUsuarioPorDataDto dto = new DesativarAtivarUsuarioPorDataDto("Estagiário", dataHoje, UsuarioStatus.ATIVO);
//
//		this.estagiarioService.desativarEstagiariosPorData(dto, "Ativo");
//
//		estagiarios = this.estagiarioRepository.findAll();
//
//		assertEquals(UsuarioStatus.ATIVO, estagiarios.get(0).getUsuarioAuth().getUsuarioStatus());
//		assertEquals(UsuarioStatus.ATIVO, estagiarios.get(1).getUsuarioAuth().getUsuarioStatus());
//	}
}
