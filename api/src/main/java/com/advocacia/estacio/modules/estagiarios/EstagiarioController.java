package com.advocacia.estacio.modules.estagiarios;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estagiarios")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class EstagiarioController {
	
	private final EstagiarioService estagiarioService;

	@PostMapping()
	public ResponseEntity<EstagiarioDTO.Response> cadastrar(
			@RequestBody @Valid EstagiarioDTO.CreateRequest request, UriComponentsBuilder uriBuilder
	) {
		EstagiarioDTO.Response response = estagiarioService.cadastrar(request);
		var uri = uriBuilder.path("/estagiarios/{id}").buildAndExpand(response.id()).toUri();
		return ResponseEntity.created(uri).body(response);
	}

	@GetMapping
	public ResponseEntity<Page<EstagiarioDTO.ListResponse>> listar(
			EstagiarioDTO.SearchFilter filtro,
			@PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
	) {
		var page = estagiarioService.listar(filtro, pageable);
		return ResponseEntity.ok(page);
	}

	@GetMapping("/opcoes")
	public ResponseEntity<List<EstagiarioDTO.OptionResponse>> listarOpcoes(
			@RequestParam(defaultValue = "") String nome
	) {
		var response = estagiarioService.listarOpcoes(nome);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EstagiarioDTO.Response> buscarPorId(@PathVariable Long id) {
		EstagiarioDTO.Response response = estagiarioService.buscarPorId(id);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EstagiarioDTO.Response> atualizar(
			@PathVariable Long id, @RequestBody @Valid EstagiarioDTO.UpdateRequest request
	) {
		EstagiarioDTO.Response response = estagiarioService.atualizar(id, request);
		return ResponseEntity.ok(response);
	}

	// TODO: Método separado p/ alterar o Período de Estágio
}
