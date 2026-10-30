package com.advocacia.estacio.modules.assistidos;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/v1/assistidos")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AssistidoController {
	
	private final AssistidoService assistidoService;

	@PostMapping
	public ResponseEntity<AssistidoDTO.Response> cadastrar(
			@RequestBody @Valid AssistidoDTO.CreateRequest request, UriComponentsBuilder uriBuilder) {
		AssistidoDTO.Response response = assistidoService.cadastrar(request);
		var uri = uriBuilder.path("/api/v1/assistidos/{id}").buildAndExpand(response.id()).toUri();
		return ResponseEntity.created(uri).body(response);
	}

	@GetMapping
	public ResponseEntity<Page<AssistidoDTO.ListResponse>> listar(
			AssistidoDTO.SearchFilter filtro,
			@PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
	) {
		String termoTratado = null;
		if (filtro != null && filtro.termo() != null && !filtro.termo().isBlank()) {
			termoTratado = filtro.termo().trim();
		}
		var filtroSeguro = new AssistidoDTO.SearchFilter(termoTratado);
		var page = assistidoService.listar(filtroSeguro, pageable);
		return ResponseEntity.ok(page);
	}

	@GetMapping("/opcoes")
	public ResponseEntity<List<AssistidoDTO.OptionResponse>> listarOpcoes(
			@RequestParam(defaultValue = "") String nome) {
		var response = assistidoService.listarOpcoes(nome);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AssistidoDTO.Response> buscarPorId(@PathVariable Long id) {
		AssistidoDTO.Response response = assistidoService.buscarPorId(id);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AssistidoDTO.Response> atualizar(
			@PathVariable Long id, @RequestBody @Valid AssistidoDTO.UpdateRequest request
	) {
		AssistidoDTO.Response response = assistidoService.atualizar(id, request);
		return ResponseEntity.ok(response);
	}
}
