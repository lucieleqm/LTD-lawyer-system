package com.advocacia.estacio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.advocacia.estacio.domain.entities.*;
import com.advocacia.estacio.modules.professores.Professor;
import com.advocacia.estacio.modules.usuarios.UsuarioStatus;
import com.advocacia.estacio.modules.usuarios.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.advocacia.estacio.domain.dto.AtorDto;
import com.advocacia.estacio.repositories.AtorRepository;
import com.advocacia.estacio.modules.usuarios.UsuarioRepository;
import com.advocacia.estacio.utils.TestUtil;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AtorServiceTest {
	
	@Autowired
	AtorService atorService;
	
	@Autowired
	AtorRepository atorRepository;
	
	@Autowired
    UsuarioRepository usuarioRepository;
	
	@Autowired
	TestUtil testUtil;
	
	@Test
	@Order(1)
	void deletando_TodosOsDados_antes_dos_testes() {
		testUtil.deleteAll();
	}

	@Test
	@Order(2)
	@DisplayName("Deve Salvar Coordenador No Banco De Dados Pelo Service")
	void salvar_coordenador() {
		assertEquals(0, atorRepository.count());
		assertEquals(0, usuarioRepository.count());
		
		Ator ator = atorService.salvar(testUtil.getAtores().get(0));
		
		assertNotNull(ator);
		assertNotNull(ator.getId());
		assertNotNull(ator.getUsuarioAuth());
		assertEquals("Roberto Carlos", ator.getNome());
		assertEquals("roberto@gmail.com", ator.getEmail());
		assertEquals("Coordenador do curso", ator.getTipoDoAtor().getTipo());
		assertEquals(UsuarioStatus.ATIVO, ator.getUsuarioAuth().getUsuarioStatus());
		
		Usuario userAuth = (Usuario) usuarioRepository.findByLogin(ator.getEmail());
		
		assertEquals(1, atorRepository.count());
		assertEquals(1, usuarioRepository.count());
		assertEquals(ator.getTipoDoAtor().getTipo(), userAuth.getRole().getRole());
	}

	@Test
	@Order(3)
	@DisplayName("Deve Salvar Secretario No Banco De Dados Pelo Service")
	void salvar_secretario() {
		
		Ator ator = atorService.salvar(testUtil.getAtores().get(1));
		
		assertNotNull(ator);
		assertNotNull(ator.getId());
		assertNotNull(ator.getUsuarioAuth());
		assertEquals("José Augusto", ator.getNome());
		assertEquals("jose@gmail.com", ator.getEmail());
		assertEquals("Secretário", ator.getTipoDoAtor().getTipo());
		assertEquals(UsuarioStatus.ATIVO, ator.getUsuarioAuth().getUsuarioStatus());
		
		Usuario userAuth = (Usuario) usuarioRepository.findByLogin(ator.getEmail());
		
		assertEquals(2, atorRepository.count());
		assertEquals(2, usuarioRepository.count());
		assertEquals(ator.getTipoDoAtor().getTipo(), userAuth.getRole().getRole());
	}
	
	@Test
	@Order(4)
	@DisplayName("Deve Salvar Professor No Banco De Dados Pelo Service")
	void salvar_professor() {
		
		Ator ator = atorService.salvar(testUtil.getAtores().get(2));
		
		assertNotNull(ator);
		assertNotNull(ator.getId());
		assertNotNull(ator.getUsuarioAuth());
		assertEquals("Fabio Junior", ator.getNome());
		assertEquals("fabio@gmail.com", ator.getEmail());
		assertEquals("Professor", ator.getTipoDoAtor().getTipo());
		assertEquals(UsuarioStatus.ATIVO, ator.getUsuarioAuth().getUsuarioStatus());
		
		Usuario userAuth = (Usuario) usuarioRepository.findByLogin(ator.getEmail());
		
		assertEquals(3, atorRepository.count());
		assertEquals(3, usuarioRepository.count());
		assertEquals(ator.getTipoDoAtor().getTipo(), userAuth.getRole().getRole());
	}
	
	@Test
	@Order(5)
	@DisplayName("Deve Atualizar Coordenador No Banco De Dados Pelo Service")
	void atualizar_coordenador() {
		
		AtorDto atorDto = new AtorDto(
				null, "Roberto Carlos Silva", "roberto22@gmail.com", 
				"Coordenador do curso", "Inativo", "1234");
		
		Long id = atorRepository.findAll().get(0).getId();
		
		Ator ator = atorService.atualizar(id, atorDto);
		
		assertNotNull(ator);
		assertEquals("roberto22@gmail.com", ator.getUsuarioAuth().getLogin());
		assertEquals("Roberto Carlos Silva", ator.getNome());
		assertEquals("roberto22@gmail.com", ator.getEmail());
		assertEquals("Coordenador do curso", ator.getTipoDoAtor().getTipo());
		assertEquals(UsuarioStatus.INATIVO, ator.getUsuarioAuth().getUsuarioStatus());
	}

	@Test
	@DisplayName("Deve buscar UsuariosAuth por lista de id")
	void buscar_usuariosAuth() {

		List<Long> ids = atorRepository.findAll().stream().map(Ator::getId).toList();

		List<Usuario> usuariosAuth = atorService.buscarUsuariosAuthPorId(ids);

		assertEquals(3, usuariosAuth.size());
		assertEquals("roberto22@gmail.com", usuariosAuth.get(0).getLogin());
		assertEquals("jose@gmail.com", usuariosAuth.get(1).getLogin());
		assertEquals("fabio@gmail.com", usuariosAuth.get(2).getLogin());
	}

	@Test
	@DisplayName("Deve buscar Todos os Coordenadores Pelo Service")
	void buscar_coordenadores() {

		Page<Ator> pages = atorService.buscarTodosPorTipoDoAtor("Coordenador do curso",0, 20);

		assertFalse(pages.isEmpty());
		assertEquals(1, pages.getContent().size());
		assertEquals("Roberto Carlos Silva", pages.getContent().get(0).getNome());
	}

	@Test
	@DisplayName("Deve buscar Todos os Secretários Pelo Service")
	void buscar_secretarios() {

		Page<Ator> pages = atorService.buscarTodosPorTipoDoAtor("Secretário",0, 20);

		assertFalse(pages.isEmpty());
		assertEquals(1, pages.getContent().size());
		assertEquals("José Augusto", pages.getContent().get(0).getNome());
	}

	@Test
	@DisplayName("Deve buscar Todos os Professores Pelo Service")
	void buscar_professores() {

		Page<Ator> pages = atorService.buscarTodosPorTipoDoAtor("Professor",0, 20);

		assertFalse(pages.isEmpty());
		assertEquals(1, pages.getContent().size());
		assertEquals("Fabio Junior", pages.getContent().get(0).getNome());
	}
	
	@Test
	@DisplayName("Deve Buscar Coordenador por Id No Banco De Dados Pelo Service")
	void buscar_coordenador_por_id() {
		
		Long id = atorRepository.findAll().get(0).getId();
		
		CoordenadorDoCurso coordenador = (CoordenadorDoCurso) atorService.buscarPorId(id);
		
		assertNotNull(coordenador);
		assertNotNull(coordenador.getId());
		assertNotNull(coordenador.getUsuarioAuth());
		assertEquals("Roberto Carlos Silva", coordenador.getNome());
		assertEquals("roberto22@gmail.com", coordenador.getEmail());
		assertEquals("Coordenador do curso", coordenador.getTipoDoAtor().getTipo());
		assertEquals(UsuarioStatus.INATIVO, coordenador.getUsuarioAuth().getUsuarioStatus());
	}
	
	@Test
	@DisplayName("Deve Buscar Secretário por Id No Banco De Dados Pelo Service")
	void buscar_secretário_por_id() {
		
		Long id = atorRepository.findAll().get(1).getId();
		
		Secretario secretario = (Secretario) atorService.buscarPorId(id);
		
		assertNotNull(secretario);
		assertNotNull(secretario.getId());
		assertNotNull(secretario.getUsuarioAuth());
		assertEquals("José Augusto", secretario.getNome());
		assertEquals("jose@gmail.com", secretario.getEmail());
		assertEquals("Secretário", secretario.getTipoDoAtor().getTipo());
		assertEquals(UsuarioStatus.ATIVO, secretario.getUsuarioAuth().getUsuarioStatus());

	}
	
	@Test
	@DisplayName("Deve Buscar Professor por Id No Banco De Dados Pelo Service")
	void buscar_professor_por_id() {
		
		Long id = atorRepository.findAll().get(2).getId();
		
		Professor professor = (Professor) atorService.buscarPorId(id);
		
		assertNotNull(professor);
		assertNotNull(professor.getId());
		assertNotNull(professor.getUsuarioAuth());
		assertEquals("Fabio Junior", professor.getNome());
		assertEquals("fabio@gmail.com", professor.getEmail());
		assertEquals("Professor", professor.getTipoDoAtor().getTipo());
		assertEquals(UsuarioStatus.ATIVO, professor.getUsuarioAuth().getUsuarioStatus());
	}

	@Test
	@DisplayName("Deve buscar Tipo de Atores Pelo Service")
	void buscar_tipo_atores() {
		List<TipoDoAtor> atores = atorService.getTipoAtores();

		assertEquals(TipoDoAtor.COORDENADOR_DO_CURSO, atores.get(0));
		assertEquals(TipoDoAtor.SECRETARIO, atores.get(1));
		assertEquals(TipoDoAtor.PROFESSOR, atores.get(2));
	}

	@Test
	@DisplayName("Deve Buscar Professor Por Nome Pelo Service")
	void buscar_professor_pelo_nome() {

		String nome = "fab";

		Page<Ator> atores = atorService.buscarAtor(nome, "Professor", 0, 10);

		assertNotNull(atores);
		assertEquals(1, atores.getContent().size());
		assertEquals("Fabio Junior", atores.getContent().get(0).getNome());
	}
}
