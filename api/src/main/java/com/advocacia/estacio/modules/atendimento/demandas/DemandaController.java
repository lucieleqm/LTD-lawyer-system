package com.advocacia.estacio.modules.atendimento.demandas;

import com.advocacia.estacio.infra.security.CustomUserDetails;
import com.advocacia.estacio.modules.atendimento.demandas.tramitacoes.DemandaTramitacaoDTO;
import com.advocacia.estacio.modules.atendimento.demandas.tramitacoes.DemandaTramitacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequestMapping("/api/v1/demandas")
@RestController
@RequiredArgsConstructor
public class DemandaController {

	private final DemandaService demandaService;
	private final DemandaTramitacaoService tramitacaoService;

	@PostMapping
	public ResponseEntity<DemandaDTO.Response> cadastrar(
			@RequestBody @Valid DemandaDTO.Request dados, @AuthenticationPrincipal CustomUserDetails usuarioLogado,
			UriComponentsBuilder uriBuilder
	) {
		DemandaDTO.Response response = demandaService.cadastrar(dados, usuarioLogado.getPessoaId());
		var uri = uriBuilder.path("/api/v1/demandas/{id}").buildAndExpand(response.id()).toUri();
		return ResponseEntity.created(uri).body(response);
	}

	@GetMapping
	public ResponseEntity<Page<DemandaDTO.ListResponse>> listar(
			DemandaDTO.SearchFilter filtro,
			@PageableDefault(size = 20, direction = Sort.Direction.DESC) Pageable pageable
	) {
		var pages = demandaService.listar(filtro, pageable);
		return ResponseEntity.ok(pages);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DemandaDTO.Response> buscarPorId(@PathVariable Long id) {
		var dto = demandaService.buscarDetalhesPorId(id);
		return ResponseEntity.ok(dto);
	}

	@PostMapping("/{demandaId}/tramitacoes")
	public ResponseEntity<DemandaTramitacaoDTO.Response> tramitar(
			@PathVariable Long demandaId,
			@RequestBody @Valid DemandaTramitacaoDTO.CreateRequest req,
			@AuthenticationPrincipal CustomUserDetails usuarioLogado
	) {
		var response = demandaService.tramitar(demandaId, req, usuarioLogado);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{demandaId}/tramitacoes")
	public ResponseEntity<Page<DemandaTramitacaoDTO.Response>> listarTramitacoes(
			@PathVariable Long demandaId,
			@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
	) {
		return ResponseEntity.ok(tramitacaoService.listarPorDemanda(demandaId, pageable));
	}

	@GetMapping({"/{demandaId}/tramitacoes/disponiveis"})
	public ResponseEntity<List<DemandaTramitacaoDTO.Disponivel>> listarAcoesDisponiveis(
			@PathVariable Long demandaId,
			@AuthenticationPrincipal CustomUserDetails usuarioLogado
	) {
		return ResponseEntity.ok((demandaService.listarAcoesDisponiveis(demandaId, usuarioLogado.getRole())));
	}

	@PutMapping("/{id}")
	public ResponseEntity<DemandaDTO.Response> atualizar(
			@PathVariable Long id, @RequestBody @Valid DemandaDTO.UpdateRequest request
	) {
		DemandaDTO.Response response = demandaService.atualizar(id, request);
		return ResponseEntity.ok(response);
	}
}

