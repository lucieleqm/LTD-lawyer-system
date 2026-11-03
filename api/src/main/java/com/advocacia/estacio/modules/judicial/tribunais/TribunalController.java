package com.advocacia.estacio.modules.judicial.tribunais;

import com.advocacia.estacio.shared.entity.Situacao;
import com.advocacia.estacio.shared.pagination.SliceResponse;
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

@RestController
@RequestMapping("/api/v1/tribunais")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class TribunalController {
    private  final TribunalService tribunalService;

    @PostMapping
    public ResponseEntity<TribunalDTO.Response> cadastrar(
            @RequestBody @Valid TribunalDTO.Request request, UriComponentsBuilder uriBuilder
    ) {
        TribunalDTO.Response response = tribunalService.cadastrar(request);
        var uri = uriBuilder.path("/tribunais/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<TribunalDTO.Response>> listar(
            @RequestParam(required = false) Situacao situacao,
            @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        var page = tribunalService.listar(situacao, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/opcoes")
    public ResponseEntity<SliceResponse<TribunalDTO.Summary>> listarOpcoes(
            @RequestParam(defaultValue = "") String nome,
            @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(tribunalService.listarOpcoes(nome, page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TribunalDTO.Response> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tribunalService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TribunalDTO.Response> atualizar(
            @PathVariable Long id, @RequestBody @Valid TribunalDTO.Request request) {
        return ResponseEntity.ok(tribunalService.atualizar(id, request));
    }

    @PatchMapping("/{id}/arquivar")
    public ResponseEntity<TribunalDTO.Response> arquivar(@PathVariable Long id) {
        return ResponseEntity.ok(tribunalService.arquivar(id));
    }

    @PatchMapping("/{id}/reativar")
    public ResponseEntity<TribunalDTO.Response> reativar(@PathVariable Long id) {
        return ResponseEntity.ok(tribunalService.reativar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        tribunalService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
