package com.advocacia.estacio.modules.demandas;

import com.advocacia.estacio.infra.exceptions.RegraDeNegocioException;
import com.advocacia.estacio.infra.security.AuthorizationUtils;
import com.advocacia.estacio.infra.security.CustomUserDetails;

import com.advocacia.estacio.modules.advogados.Advogado;
import com.advocacia.estacio.modules.demandas.tramitacoes.DemandaTramitacao;
import com.advocacia.estacio.modules.demandas.tramitacoes.DemandaTramitacaoDTO;
import com.advocacia.estacio.modules.demandas.tramitacoes.TipoTramitacao;
import com.advocacia.estacio.modules.estagiarios.Estagiario;
import com.advocacia.estacio.modules.estagiarios.EstagiarioService;
import com.advocacia.estacio.modules.advogados.AdvogadoService;
import com.advocacia.estacio.modules.pessoas.Pessoa;
import com.advocacia.estacio.modules.pessoas.PessoaService;
import com.advocacia.estacio.modules.professores.Professor;
import com.advocacia.estacio.modules.professores.ProfessorService;
import com.advocacia.estacio.infra.security.SecurityUtils;
import com.advocacia.estacio.modules.usuarios.UsuarioRole;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DemandaService {

	private final DemandaRepository demandaRepository;
	private final EstagiarioService estagiarioService;
	private final ProfessorService professorService;
	private final AdvogadoService advogadoService;
	private final PessoaService pessoaService;
	private final SecurityUtils securityUtils;
	private final AuthorizationUtils authz;


	/**
	 * Ao criar uma demanda se add partes envolvidas na demanda
	 * Adicionar os dias para calcular o Prazo Final (Prazo dos Docs + dias adicionais)
	 */
	@Transactional
	public DemandaDTO.Response cadastrar(DemandaDTO.Request req, Long pessoaLogadaId) {
		Demanda demanda = req.toEntity(
				advogadoService.obterReferencia(req.advogadoId()),
				estagiarioService.obterReferecia(req.estagiarioId()),
				professorService.obterReferencia(req.professorId())
		);
		Pessoa responsavel = pessoaService.obterReferencia(pessoaLogadaId);

		DemandaTramitacao tramitacaoInicial = DemandaTramitacao.builder()
				.responsavel(responsavel)
				.tipoTramitacao(TipoTramitacao.ABERTURA)
				.observacoes("Demanda cadastrada e iniciada no sistema.")
				.build();

		demanda.adicionarTramitacao(tramitacaoInicial);
		var demandaSalva = demandaRepository.save(demanda);
		return new DemandaDTO.Response(demandaSalva);
	}

	public DemandaDTO.Response buscarDetalhesPorId(Long id) {
		var demanda = demandaRepository.buscarDetalhesPorId(id)
				.orElseThrow(() -> new EntityNotFoundException("Demanda não encontrada"));
		return new DemandaDTO.Response(demanda);
	}

	public Page<DemandaDTO.ListResponse> listar(DemandaDTO.SearchFilter filtro, Pageable pageable) {
		boolean isAdmin = authz.isAdmin();
		Specification<Demanda> spec = DemandaSpecs.comFiltros(filtro);

		if(!isAdmin) {
			Long pessoaId = securityUtils.getIdPessoaLogada();
			spec = spec.and(DemandaSpecs.envolvePessoa(pessoaId));
		}

		return demandaRepository.findAll(spec, pageable).map(DemandaDTO.ListResponse::new);
	}

	@Transactional
	public DemandaTramitacaoDTO.Response tramitar(
			Long demandaId, DemandaTramitacaoDTO.CreateRequest req, CustomUserDetails usuarioLogado
	) {
		Demanda demanda = buscarDemandaPorId(demandaId);
		TipoTramitacao acao = req.tipoTramitacao();

		if (demanda.getEtapaAtual().isFinalizada()) {
			throw new RegraDeNegocioException("Não é possível tramitar uma demanda já concluída ou arquivada.");
		}
		if (!acao.podeSerExecutadaPor(usuarioLogado.getRole())) {
			throw new AccessDeniedException("Você não tem permissão para executar esta tramitação.");
		}
		if (!acao.podeSerExecutadaNa(demanda.getEtapaAtual())) {
			throw new RegraDeNegocioException(
					"A tramitação '" + acao.name() + "' não pode ser executada enquanto a demanda estiver na etapa '" + demanda.getEtapaAtual().name() + "'."
			);
		}

		Pessoa responsavel = pessoaService.obterReferencia(usuarioLogado.getPessoaId());
		DemandaTramitacao tramitacao = req.toEntity(responsavel);
		demanda.adicionarTramitacao(tramitacao);

		demandaRepository.saveAndFlush(demanda);
		return new DemandaTramitacaoDTO.Response(tramitacao);
	}

	public List<DemandaTramitacaoDTO.Disponivel> listarAcoesDisponiveis(Long id, UsuarioRole roleLogado) {
		Demanda demanda = buscarDemandaPorId(id);

		return Arrays.stream(TipoTramitacao.values())
				.filter(acao -> acao.podeSerExecutadaNa(demanda.getEtapaAtual()))
				.filter(acao -> acao.podeSerExecutadaPor(roleLogado))
				.map(acao -> new DemandaTramitacaoDTO.Disponivel(acao, acao.getDescricao()))
				.toList();
	}

	// Pendente de avaliação
	@Transactional
	public DemandaDTO.Response atualizar(Long id, DemandaDTO.UpdateRequest req) {
		Demanda demanda = demandaRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Demanda não encontrada com ID: " + id));

		Advogado advogado = advogadoService.obterReferencia(req.advogadoId());
		Estagiario estagiario = estagiarioService.obterReferecia(req.estagiarioId()); // corrigir erro de pt
		Professor professor = professorService.obterReferencia(req.professorId());

		demanda.atualizarDados(
				advogado,
				estagiario,
				professor,
				req.descricao(),
				req.prazoDocumentos(),
				req.diasAdicionais(),
				req.etapaAtual()
		);
		return new DemandaDTO.Response(demanda);
	}

	// --- MÉTODOS PRIVADOS (Auxiliares internos) ---

	/**
	 * Centraliza a busca e a regra de "Não Encontrado",
	 * evitando duplicação de código.
	 */
	private Demanda buscarDemandaPorId(Long id) {
		return demandaRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Demanda não encontrada"));
	}
}
