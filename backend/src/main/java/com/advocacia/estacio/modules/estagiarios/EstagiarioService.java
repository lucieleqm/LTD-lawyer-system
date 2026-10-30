package com.advocacia.estacio.modules.estagiarios;

import com.advocacia.estacio.modules.usuarios.Usuario;
import com.advocacia.estacio.modules.usuarios.UsuarioRole;
import com.advocacia.estacio.modules.usuarios.UsuarioService;
import com.advocacia.estacio.modules.usuarios.UsuarioStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstagiarioService {

	private final EstagiarioRepository estagiarioRepository;
	private final UsuarioService usuarioService;

	@Transactional
	public EstagiarioDTO.Response cadastrar(EstagiarioDTO.CreateRequest req) {
		Usuario user = usuarioService.cadastrar(req.email(), req.senha(), UsuarioRole.ESTAGIARIO);
		Estagiario estagiario = req.toEntity(user);
		estagiario = estagiarioRepository.save(estagiario);
		return new EstagiarioDTO.Response(estagiario);
	}

	@Transactional(readOnly = true)
	public Page<EstagiarioDTO.ListResponse> listar(EstagiarioDTO.SearchFilter filtro, Pageable pageable) {
		Specification<Estagiario> spec = EstagiarioSpecs.usandoFiltro(filtro);
		return estagiarioRepository.findAll(spec, pageable).map(EstagiarioDTO.ListResponse::new);
	}

	@Transactional(readOnly = true)
	public List<EstagiarioDTO.OptionResponse> listarOpcoes(String nome) {
		Pageable limite = PageRequest.of(0, 20);
		List<UsuarioStatus> statusPermitidos = List.of(UsuarioStatus.ATIVO, UsuarioStatus.BLOQUEADO);
		return  estagiarioRepository.buscarAtivosPorNome(nome, statusPermitidos, limite);
	}

	public EstagiarioDTO.Response buscarPorId(Long id) {
		Estagiario estagiario = estagiarioRepository.buscarDetalhesPorId(id)
				.orElseThrow(() -> new EntityNotFoundException("Estagiário não encontrado"));
		return new EstagiarioDTO.Response(estagiario);
	}

	@Transactional
	public EstagiarioDTO.Response atualizar(Long id, EstagiarioDTO.UpdateRequest req) {
		Estagiario estagiario = estagiarioRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Estagiário não encontrado"));
		estagiario.atualizarDados(req.nome(), req.telefone(), req.matricula(), req.periodoEstagio());
		return new EstagiarioDTO.Response(estagiario);
	}


	/**
	 * Retorna apenas a referência (Proxy) da entidade Estagiario para uso em chaves estrangeiras.
	 * NÃO executa um SELECT no banco de dados.
	 */
	public Estagiario obterReferecia(Long id) {
		return estagiarioRepository.getReferenceById(id);
	}
}
