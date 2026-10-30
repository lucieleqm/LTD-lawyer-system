package com.advocacia.estacio.modules.advogados;

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
@RequestMapping("/api/v1/advogados")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdvogadoController {

	private final AdvogadoService advogadoService;

	@PostMapping
	public ResponseEntity<AdvogadoDTO.Response> cadastrar(
			@RequestBody @Valid AdvogadoDTO.CreateRequest request, UriComponentsBuilder uriBuilder
	) {
		AdvogadoDTO.Response response = advogadoService.cadastrar(request);
		var uri = uriBuilder.path("/api/v1/advogados/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
	}

	@GetMapping
	public ResponseEntity<Page<AdvogadoDTO.ListResponse>> listar(
			@RequestParam(required = false) AdvogadoDTO.SearchFilter filtro,
			@PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
	) {
		var filtroSeguro = (filtro != null) ? filtro : new AdvogadoDTO.SearchFilter(null, null);
		var page = advogadoService.listar(filtroSeguro, pageable);
		return ResponseEntity.ok(page);
	}

	@GetMapping("/opcoes")
	public ResponseEntity<List<AdvogadoDTO.OptionResponse>> listarOpcoes(
			@RequestParam(defaultValue = "") String nome) {
		var response = advogadoService.listarOpcoes(nome);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AdvogadoDTO.Response> buscarPorId(@PathVariable Long id) {
		AdvogadoDTO.Response response = advogadoService.buscarPorId(id);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AdvogadoDTO.Response> atualizar(
			@PathVariable Long id, @RequestBody @Valid AdvogadoDTO.UpdateRequest request
	) {
		AdvogadoDTO.Response response = advogadoService.atualizar(id, request);
		return ResponseEntity.ok(response);
	}
//	}
//
//	@GetMapping("/buscarId/email/{email}")
//	public ResponseEntity<EntidadeMinDto> buscarIdPorEmail(@PathVariable String email) {
//		Advogado advogado = advogadoService.buscarIdPorEmail(email);
//		EntidadeMinDto dto = new EntidadeMinDto(advogado.getId(), advogado.getNome());
//		return ResponseEntity.ok(dto);
//	}
//

//

}
