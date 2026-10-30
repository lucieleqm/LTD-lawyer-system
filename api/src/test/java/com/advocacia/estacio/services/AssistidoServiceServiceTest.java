package com.advocacia.estacio.services;

import com.advocacia.estacio.modules.assistidos.AssistidoService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.advocacia.estacio.modules.assistidos.Assistido;
import com.advocacia.estacio.modules.pessoas.EstadoCivil;
import com.advocacia.estacio.modules.assistidos.AssistidoRepository;
import com.advocacia.estacio.utils.TestUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AssistidoServiceServiceTest {
	
	@Autowired
    AssistidoService assistidoService;
	
	@Autowired
	AssistidoRepository assistidoRepository;
	
	AssistidoDto assistidoDto;
	
	@Autowired
	TestUtil testUtil;
	
	@BeforeEach
	void setUp() {
		assistidoDto = testUtil.getAssistidoDto();
	}
	
	@Test
	@Order(1)
	void deletandoTodosOsDadosAntesDostestes() {
		testUtil.deleteAll();
	}

	@Test
	@Order(2)
	@DisplayName("Deve Salvar Assistido No Banco De Dados Pelo Service")
	void salvar_assistido() {
		assertEquals(0, assistidoRepository.count());
		
		Assistido assistido = assistidoService.salvar(assistidoDto);
		
		assertNotNull(assistido);
		assertNotNull(assistido.getId());
		assertEquals("Ana Carla", assistido.getNome());
		assertEquals("20250815", assistido.getMatricula());
		assertEquals("86766523354", assistido.getTelefone());
		assertEquals("ana@gmail.com", assistido.getEmail());
		assertEquals("Cientista de Dados", assistido.getProfissao());
		assertEquals("brasileiro", assistido.getNacionalidade());
		assertEquals("São Luís/MA", assistido.getNaturalidade());
		assertEquals(EstadoCivil.SOLTERIO, assistido.getEstadoCivil());
		
		assertEquals(1, assistidoRepository.count());

	}
	
	@Test
	@Order(3)
	@DisplayName("Deve Atualizar Assistido No Banco De Dados Pelo Service")
	void atualizar_assistido() {
		
		AssistidoDto assistidoDto = new AssistidoDto(
				null, "Ana Carla Silva", "20250815", "86766523354",
		"ana22@gmail.com", "Cientista de Dados", "brasileiro", 
		"São Luís/MA", "Casado(a)", "São Luís", "Vila dos Nobres", 
		"rua dos nobres", 12, "43012-232");
		
		Long id = assistidoRepository.findAll().get(0).getId();
		
		Assistido assistido = assistidoService.atualizar(id, assistidoDto);
		
		assertNotNull(assistido);
		assertEquals("Ana Carla Silva", assistido.getNome());
		assertEquals("20250815", assistido.getMatricula());
		assertEquals("86766523354", assistido.getTelefone());
		assertEquals("ana22@gmail.com", assistido.getEmail());
		assertEquals("Cientista de Dados", assistido.getProfissao());
		assertEquals("brasileiro", assistido.getNacionalidade());
		assertEquals("São Luís/MA", assistido.getNaturalidade());
		assertEquals(EstadoCivil.CASADO, assistido.getEstadoCivil());
		assertEquals("Vila dos Nobres", assistido.getEndereco().getBairro());
		
		assertEquals(1, assistidoRepository.count());

	}
	
	@Test
	@DisplayName("Deve Salvar Assistido Por Id No Banco De Dados Pelo Service")
	void buscar_por_id() {
		Long id = assistidoRepository.findAll().get(0).getId();
		
		Assistido assistido = assistidoService.buscarPorId(id);
		
		assertNotNull(assistido);
		assertNotNull(assistido.getId());
		assertEquals("Ana Carla Silva", assistido.getNome());
		assertEquals("20250815", assistido.getMatricula());
		assertEquals("86766523354", assistido.getTelefone());
		assertEquals("ana22@gmail.com", assistido.getEmail());
		assertEquals("Cientista de Dados", assistido.getProfissao());
		assertEquals("brasileiro", assistido.getNacionalidade());
		assertEquals("São Luís/MA", assistido.getNaturalidade());
		assertEquals(EstadoCivil.CASADO, assistido.getEstadoCivil());
	}
	
	@Test
	@DisplayName("Deve Buscar Assistido Por Nome Pelo Service")
	void buscar_assistido_por_nome() throws Exception {
		
		Page<Assistido> pages = assistidoService.buscarAssistidoPorNome("Car", 0, 20);
		assertEquals(1, pages.getContent().size());
		assertEquals("Ana Carla Silva", pages.getContent().get(0).getNome());
	}

	@Test
	@DisplayName("Deve buscar Todos os Assistidos Pelo Service")
	void buscar_todos() {

		Page<ResponseMinDto> pages = assistidoService.buscarTodos(0, 20);

		assertFalse(pages.isEmpty());
		assertEquals(1, pages.getContent().size());
	}

	@Test
	@DisplayName("Deve buscar Estados Civis Pelo Service")
	void buscar_estados_civis() {

		List<EstadoCivil> estadoCivis = assistidoService.getEstadosCivis();

		assertEquals(EstadoCivil.SOLTERIO, estadoCivis.get(0));
		assertEquals(EstadoCivil.CASADO, estadoCivis.get(1));
		assertEquals(EstadoCivil.DIVORCIADO, estadoCivis.get(2));
		assertEquals(EstadoCivil.VIUVO, estadoCivis.get(3));
		assertEquals(EstadoCivil.SEPARADO_JUDICIALMENTE, estadoCivis.get(4));
	}
}
