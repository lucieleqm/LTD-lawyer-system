package com.advocacia.estacio.modules.judicial.orgaos_julgadores;

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
@RequestMapping("/api/v1/orgaos-julgadores")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class OrgaoJulgadorController {
    private  final OrgaoJulgadorService orgaoJulgadorService;

    @PostMapping
    public ResponseEntity<OrgaoJulgadorDTO.Response> cadastrar(
            @RequestBody @Valid OrgaoJulgadorDTO.Request request, UriComponentsBuilder uriBuilder
    ) {
        OrgaoJulgadorDTO.Response response = orgaoJulgadorService.cadastrar(request);
        var uri = uriBuilder.path("/orgaos-julgadores/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<OrgaoJulgadorDTO.Response>> listar(
            @RequestParam(required = false) Situacao situacao,
            @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        var page = orgaoJulgadorService.listar(situacao, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/opcoes")
    public ResponseEntity<SliceResponse<OrgaoJulgadorDTO.Summary>> listarOpcoes(
            @RequestParam(defaultValue = "") String nome,
            @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(orgaoJulgadorService.listarOpcoes(nome, page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrgaoJulgadorDTO.Response> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(orgaoJulgadorService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrgaoJulgadorDTO.Response> atualizar(
            @PathVariable Long id, @RequestBody @Valid OrgaoJulgadorDTO.Request request) {
        return ResponseEntity.ok(orgaoJulgadorService.atualizar(id, request));
    }

    @PatchMapping("/{id}/arquivar")
    public ResponseEntity<OrgaoJulgadorDTO.Response> arquivar(@PathVariable Long id) {
        return ResponseEntity.ok(orgaoJulgadorService.arquivar(id));
    }

    @PatchMapping("/{id}/reativar")
    public ResponseEntity<OrgaoJulgadorDTO.Response> reativar(@PathVariable Long id) {
        return ResponseEntity.ok(orgaoJulgadorService.reativar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        orgaoJulgadorService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
