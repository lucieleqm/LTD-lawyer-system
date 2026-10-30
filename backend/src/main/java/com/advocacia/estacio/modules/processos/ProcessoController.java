package com.advocacia.estacio.modules.processos;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("api/v1/processos")
@RequiredArgsConstructor
public class ProcessoController {

	private final ProcessoService processoService;

	@PostMapping
	public ResponseEntity<ProcessoDTO.Response> cadastrar(
            @RequestBody @Valid ProcessoDTO.CreateRequest req, UriComponentsBuilder uriBuilder) {
        var response = processoService.cadastrar(req);
        var uri = uriBuilder.path("/api/v1/processos/{id}").buildAndExpand(response.id()).toUri();
		return ResponseEntity.created(uri).body(response);
	}

	@GetMapping
	public ResponseEntity<Page<ProcessoDTO.ListResponse>> listar(
			ProcessoDTO.SearchFilter filtro,
			@PageableDefault(size = 20, direction = Sort.Direction.DESC) Pageable pageable
	) {
		var pages = processoService.listar(filtro, pageable);
		return ResponseEntity.ok(pages);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProcessoDTO.Response> buscarPorId(@PathVariable Long id) {
		var dto  = processoService.buscarDetalhesPorId(id);
		return ResponseEntity.ok(dto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProcessoDTO.Response> atualizar(
			@PathVariable Long id, @RequestBody @Valid ProcessoDTO.UpdateRequest request
	) {
		ProcessoDTO.Response response = processoService.atualizar(id, request);
		return ResponseEntity.ok(response);
	}
}
