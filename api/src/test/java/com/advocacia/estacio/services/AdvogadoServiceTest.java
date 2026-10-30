package com.advocacia.estacio.services;

import com.advocacia.estacio.domain.entities.Ator;
import com.advocacia.estacio.modules.usuarios.Usuario;
import com.advocacia.estacio.modules.usuarios.UsuarioStatus;
import com.advocacia.estacio.modules.advogados.AdvogadoService;
import com.advocacia.estacio.modules.usuarios.UsuarioRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.advocacia.estacio.modules.advogados.Advogado;
import com.advocacia.estacio.modules.advogados.AdvogadoRepository;
import com.advocacia.estacio.utils.TestUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdvogadoServiceTest {
	
	@Autowired
	AdvogadoService advogadoService;
	
	@Autowired
	AdvogadoRepository advogadoRepository;

	@Autowired
    UsuarioRepository usuarioRepository;
	
	@Autowired
	TestUtil testUtil;
	
	AdvogadoDto advogadoDto;
	
	@BeforeEach
	void setUp() {
		advogadoDto = testUtil.getAdvogadoDto();
	}
	
	@Test
	@Order(1)
	void deletando_TodosOsDados_DepoisDostestes() {
		testUtil.deleteAll();
	}

	@Test
	@Order(2)
	@DisplayName("Deve Salvar Advogado No Banco De Dados Pelo Service")
	void salvar_advogado() {
		assertEquals(0, advogadoRepository.count());
		
		Advogado advogado = advogadoService.salvar(advogadoDto);
		
		assertNotNull(advogado);
		assertNotNull(advogado.getId());
		assertEquals("Carlos Silva", advogado.getNome());
		assertEquals("carlos@gmail.com", advogado.getEmail());
		assertEquals("88566519808", advogado.getTelefone());
		assertEquals("1996-09-25", advogado.getDataDeNascimeto().toString());
		assertEquals(UsuarioStatus.ATIVO, advogado.getUsuarioAuth().getUsuarioStatus());
		
		assertEquals(1, advogadoRepository.count());
		assertEquals(1, usuarioRepository.count());

	}
	
	@Test
	@Order(3)
	@DisplayName("Deve Atualizar Advogado No Banco De Dados Pelo Service")
	void atualizar_advogado() {
		
		AdvogadoDto advogadoDto = new AdvogadoDto(null, "Carlos Silva Lima", "carlos22@gmail.com",
		"88566519122", "24/08/1993", "São Luís", "Vila dos Nobres",
		"rua do passeio", 11, "53022-112", "Inativo", "1234");
		
		Long id = advogadoRepository.findAll().get(0).getId();
		
		Advogado advogadoAtualizado = advogadoService.atualizar(id, advogadoDto);
		
		assertNotNull(advogadoAtualizado);
		assertEquals("Carlos Silva Lima", advogadoAtualizado.getNome());
		assertEquals("carlos22@gmail.com", advogadoAtualizado.getEmail());
		assertEquals("88566519122", advogadoAtualizado.getTelefone());
		assertEquals("1993-08-24", advogadoAtualizado.getDataDeNascimeto().toString());
		assertEquals("Vila dos Nobres", advogadoAtualizado.getEndereco().getBairro());
		assertEquals(UsuarioStatus.INATIVO, advogadoAtualizado.getUsuarioAuth().getUsuarioStatus());
		
		assertEquals(1, advogadoRepository.count());
	}

	@Test
	@DisplayName("Deve buscar UsuariosAuth por lista de id")
	void buscar_usuariosAuth() {

		advogadoService.salvar(testUtil.getAdvogadoDto2());

		List<Long> ids = advogadoRepository.findAll().stream().map(Advogado::getId).toList();

		List<Usuario> usuariosAuth = advogadoService.buscarUsuariosAuthPorId(ids);

		assertNotNull(usuariosAuth);
		assertEquals(2, usuariosAuth.size());
		assertEquals("carlos22@gmail.com", usuariosAuth.get(0).getLogin());
		assertEquals("mauricio@gmail.com", usuariosAuth.get(1).getLogin());

	}
	
	@Test
	@DisplayName("Deve Buscar Advogado Por Nome Pelo Service")
	void buscar_advogado_pelo_nome() {
		
		String nome = "il";
		
		Page<Advogado> advogados = advogadoService.buscarAdvogado(nome, 0, 10);
		
		assertNotNull(advogados);
		assertEquals(1, advogados.getContent().size());
		assertEquals("Carlos Silva Lima", advogados.getContent().get(0).getNome());
	}

	@Test
	@DisplayName("Deve buscar Todos os advogados Pelo Service")
	void buscar_todos() {

		Page<ResponseMinDto> pages = advogadoService.buscarTodos(0, 20);

		assertFalse(pages.isEmpty());
		assertEquals(1, pages.getContent().size());
	}
	
	@Test
	@DisplayName("Deve buscar Advogado Por Id Pelo Service")
	void buscar_advogado_por_id() {

		Long id = advogadoRepository.findAll().get(0).getId();
		
		Advogado advogado = advogadoService.buscarPorId(id);

		assertNotNull(advogado);
		assertEquals("Carlos Silva Lima", advogado.getNome());
		assertEquals("carlos22@gmail.com", advogado.getEmail());
		assertEquals("88566519122", advogado.getTelefone());
		assertEquals("1993-08-24", advogado.getDataDeNascimeto().toString());
		assertEquals("São Luís", advogado.getEndereco().getCidade());
		assertEquals("Vila dos Nobres", advogado.getEndereco().getBairro());
		assertEquals("rua do passeio", advogado.getEndereco().getRua());
		assertEquals(11, advogado.getEndereco().getNumeroDaCasa());
		assertEquals("53022-112", advogado.getEndereco().getCep());
		assertEquals(UsuarioStatus.INATIVO, advogado.getUsuarioAuth().getUsuarioStatus());
	}

	@Test
	@DisplayName("Deve buscar Advogado ID e NOME pelo por Email Pelo Service")
	void buscar_advogado_id_e_nome_por_email() {

		Advogado advogado = advogadoRepository.findAll().get(0);
		Long id = advogado.getId();
		String nome = advogado.getNome();
		String email = advogado.getEmail();

		Advogado advogadoEncontrado = advogadoService.buscarIdPorEmail(email);

		assertNotNull(advogadoEncontrado);
		assertEquals(id, advogadoEncontrado.getId());
		assertEquals("Carlos Silva Lima", advogado.getNome());
	}
}
