package com.advocacia.estacio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.advocacia.estacio.modules.pessoas.enderecos.EnderecoService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.advocacia.estacio.modules.pessoas.enderecos.Endereco;
import com.advocacia.estacio.modules.pessoas.enderecos.EnderecoRepository;
import com.advocacia.estacio.utils.TestUtil;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EnderecoServiceTest {
	
	@Autowired
    EnderecoService enderecoService;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
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
	@DisplayName("Deve Salvar Endereço Pelo Service")
	void salvar_endereco() {
		
		Endereco endereco = enderecoService.salvar(assistidoDto);
		
		assertNotNull(endereco);
		assertNotNull(endereco.getId());
		assertEquals("São Luís", endereco.getCidade());
		assertEquals("Vila Palmeira", endereco.getBairro());
		assertEquals("rua dos nobres", endereco.getRua());
		assertEquals(12, endereco.getNumeroDaCasa());
		assertEquals("43012-232", endereco.getCep());
		assertEquals(1, enderecoRepository.count());
	}
	
	@Test
	@DisplayName("Deve Atualizar Endereço Pelo Service")
	void atualizar_endereco() {
		
		Endereco endereco = new Endereco(
				null, "São Luís", "Vila dos Nobres", "rua palmeira", 2, "11012-402");
		
		Long id = enderecoRepository.findAll().get(0).getId();
		Endereco enderecoAtualizado = enderecoService.atualizar(id, endereco);
		
		assertNotNull(enderecoAtualizado);
		assertEquals("São Luís", enderecoAtualizado.getCidade());
		assertEquals("Vila dos Nobres", enderecoAtualizado.getBairro());
		assertEquals("rua palmeira", enderecoAtualizado.getRua());
		assertEquals(2, enderecoAtualizado.getNumeroDaCasa());
		assertEquals("11012-402", enderecoAtualizado.getCep());
	}
}
