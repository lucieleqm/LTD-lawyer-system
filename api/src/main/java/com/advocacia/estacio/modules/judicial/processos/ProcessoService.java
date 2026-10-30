package com.advocacia.estacio.modules.judicial.processos;

import com.advocacia.estacio.infra.exceptions.ConflitoDeDadosException;
import com.advocacia.estacio.infra.security.AuthorizationUtils;
import com.advocacia.estacio.infra.security.SecurityUtils;
import com.advocacia.estacio.modules.membros.advogados.Advogado;
import com.advocacia.estacio.modules.membros.advogados.AdvogadoService;
import com.advocacia.estacio.modules.atendimento.assistidos.Assistido;
import com.advocacia.estacio.modules.atendimento.assistidos.AssistidoService;
import com.advocacia.estacio.modules.membros.estagiarios.Estagiario;
import com.advocacia.estacio.modules.membros.estagiarios.EstagiarioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessoService {

	private final ProcessoRepository processoRepository;
	private final AssistidoService assistidoService;
	private final AdvogadoService advogadoService;
	private final EstagiarioService estagiarioService;
	private final SecurityUtils securityUtils;
	private final AuthorizationUtils authz;

	@Transactional
	public ProcessoDTO.Response cadastrar(ProcessoDTO.CreateRequest req) {
		validarNumeroPjeUnico(req.numeroProcessoPje());
		Long sequencial = processoRepository.obterProximoSequencial();

		Assistido assistido = assistidoService.obterReferencia(req.assistidoId());
		Advogado advogado = advogadoService.obterReferencia(req.advogadoId());
		Estagiario estagiario = estagiarioService.obterReferecia(req.estagiarioId());

		Processo processo = req.toEntity(assistido, advogado, estagiario);
		processo.atribuirNumeroDeProcesso(sequencial);
		processo = processoRepository.save(processo);
		return new ProcessoDTO.Response(processo);
	}

	@Transactional(readOnly = true)
	public Page<ProcessoDTO.ListResponse> listar(ProcessoDTO.SearchFilter filtro, Pageable pageable) {
		boolean isAdmin = authz.isAdmin();
		Specification<Processo> spec = ProcessoSpecs.comFiltros(filtro);

		if(!isAdmin) {
			Long pessoaId = securityUtils.getIdPessoaLogada();
			spec = spec.and(ProcessoSpecs.envolvePessoa(pessoaId));
		}

		return processoRepository.findAll(spec, pageable).map(ProcessoDTO.ListResponse::new);
	}

	@Transactional(readOnly = true)
	public ProcessoDTO.Response buscarDetalhesPorId(Long id) {
		var processo = processoRepository.buscarDetalhesPorId(id)
				.orElseThrow(() -> new EntityNotFoundException("Processo não encontrado"));
		return new ProcessoDTO.Response(processo);
	}

	// Pendente de avaliação
	@Transactional
	public ProcessoDTO.Response atualizar(Long id, ProcessoDTO.UpdateRequest req) {
		Processo processo = processoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Processo não encontrado"));

		if (req.numeroProcessoPje() != null && !req.numeroProcessoPje().isBlank()
				&& !req.numeroProcessoPje().equals(processo.getNumeroProcessoPje())) {
			validarNumeroPjeUnico(req.numeroProcessoPje());
		}

		Assistido assistido = assistidoService.obterReferencia(req.assistidoId());
		Advogado advogado = advogadoService.obterReferencia(req.advogadoId());
		Estagiario estagiario = estagiarioService.obterReferecia(req.estagiarioId());

		processo.atualizarDados(
				advogado,
				estagiario,
				assistido,
				req.numeroProcessoPje(),
				req.areaDireito(),
				req.assunto(),
				req.vara(),
				req.tribunal(),
				req.status(),
				req.prazoFinal()
		);

		if (req.partesEnvolvidas() != null) {
			List<ParteEnvolvida> novasPartes = req.partesEnvolvidas().stream()
					.map(ProcessoDTO.ParteEnvolvidaRequest::toEntity)
					.toList();

			processo.atualizarPartesEnvolvidas(novasPartes);
		}

		return new ProcessoDTO.Response(processo);
	}

	// ----------- MÉTODOS PRIVADOS AUXILIARES ----------------

	private void validarNumeroPjeUnico(String numeroPje) {
	if (numeroPje == null || numeroPje.isBlank()) {
		return;
	}

	if (processoRepository.existsByNumeroProcessoPje(numeroPje)) {
		throw new ConflitoDeDadosException("Já existe um processo cadastrado com este número de PJe.");
	}
}
}
