package com.advocacia.estacio.modules.judicial.areas_direito;

import com.advocacia.estacio.shared.entity.Situacao;
import com.advocacia.estacio.shared.pagination.SliceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Áreas do Direito", description = "Cadastro de referência das áreas do direito")
@RestController
@RequestMapping("/api/v1/areas-direito")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AreaDireitoController {
    private  final AreaDireitoService areaDireitoService;

    @ApiResponse(responseCode = "409", description = "Nome já cadastrado")
    @PostMapping
    public ResponseEntity<AreaDireitoDTO.Response> cadastrar(
            @RequestBody @Valid AreaDireitoDTO.Request request, UriComponentsBuilder uriBuilder
    ) {
        AreaDireitoDTO.Response response = areaDireitoService.cadastrar(request);
        var uri = uriBuilder.path("/api/v1/areas-direito/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<AreaDireitoDTO.Response>> listar(
            @RequestParam(required = false) Situacao situacao,
            @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        var page = areaDireitoService.listar(situacao, pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Lista opções para seleção",
            description = "Retorna apenas áreas ativas, paginadas por scroll infinito.")
    @GetMapping("/opcoes")
    public ResponseEntity<SliceResponse<AreaDireitoDTO.Summary>> listarOpcoes(
            @RequestParam(defaultValue = "") String nome,
            @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(areaDireitoService.listarOpcoes(nome, page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaDireitoDTO.Response> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(areaDireitoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AreaDireitoDTO.Response> atualizar(
            @PathVariable Long id, @RequestBody @Valid AreaDireitoDTO.Request request) {
        return ResponseEntity.ok(areaDireitoService.atualizar(id, request));
    }

    @PatchMapping("/{id}/arquivar")
    public ResponseEntity<AreaDireitoDTO.Response> arquivar(@PathVariable Long id) {
        return ResponseEntity.ok(areaDireitoService.arquivar(id));
    }

    @PatchMapping("/{id}/reativar")
    public ResponseEntity<AreaDireitoDTO.Response> reativar(@PathVariable Long id) {
        return ResponseEntity.ok(areaDireitoService.reativar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        areaDireitoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
