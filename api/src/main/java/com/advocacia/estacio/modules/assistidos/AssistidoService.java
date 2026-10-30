package com.advocacia.estacio.modules.assistidos;

import com.advocacia.estacio.modules.pessoas.enderecos.EnderecoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.modules.pessoas.enderecos.Endereco;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssistidoService {

	private final AssistidoRepository assistidoRepository;
	private final EnderecoService enderecoService;

	@Transactional
	public AssistidoDTO.Response cadastrar(AssistidoDTO.CreateRequest req) {
		Endereco endereco = enderecoService.cadastrar(req.endereco());
		Assistido assistido = req.toEntity(endereco);
		assistido = assistidoRepository.save(assistido);
		return new AssistidoDTO.Response(assistido);
	}

	@Transactional(readOnly = true)
	public List<AssistidoDTO.OptionResponse> listarOpcoes(String nome) {
		Pageable limite = PageRequest.of(0, 20);
		return  assistidoRepository.buscarOpcoesPorNome(nome, limite);
	}

	@Transactional(readOnly = true)
	public Page<AssistidoDTO.ListResponse> listar(AssistidoDTO.SearchFilter filtro, Pageable pageable) {
		return assistidoRepository.buscarPorTermoGlobal(filtro.termo(), pageable)
				.map(AssistidoDTO.ListResponse::new);
	}

	@Transactional(readOnly = true)
	public AssistidoDTO.Response buscarPorId(Long id) {
		var assistido = assistidoRepository.buscarDetalhesPorId(id)
				.orElseThrow(() -> new EntityNotFoundException("Assistido não encontrado!"));
		return new AssistidoDTO.Response(assistido);
	}

	@Transactional
	public AssistidoDTO.Response atualizar(Long id, AssistidoDTO.UpdateRequest req) {
		Assistido assistido = buscarAssistidoPorId(id);
		assistido.atualizarDados(
				req.nome(),
				req.telefone(),
				req.dataNascimento(),
				req.matricula(),
				req.profissao(),
				req.nacionalidade(),
				req.naturalidade(),
				req.estadoCivil()
		);

		Endereco endereco = enderecoService.cadastrarOuAtualizar(assistido.getPessoa().getEndereco(), req.endereco());
		assistido.getPessoa().vincularEndereco(endereco);

		return new AssistidoDTO.Response(assistido);
	}

	/**
	 * Retorna apenas a referência (Proxy) da entidade Assistido para vincular relacionamentos.
	 * NÃO executa um SELECT no banco de dados, bom para performance.
	 * @param id
	 * @return um proxy da entidade com o identificador fornecido.
	 */
	public Assistido obterReferencia(Long id) {
		return assistidoRepository.getReferenceById(id);
	}

	// --- MÉTODOS PRIVADOS (Auxiliares internos) ---

	/**
	 * Centraliza a busca e a regra de "Não Encontrado",
	 * evitando duplicação de código.
	 */
	private Assistido buscarAssistidoPorId(Long id) {
		return assistidoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Assistido não encontrado"));
	}

}
