package com.advocacia.estacio.modules.administrativo;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/administrativos")
@RequiredArgsConstructor
@Tag(name = "Administrativos", description = "Endpoints para gestão de Coordenadores e Secretários")
public class AdministrativoController {
    private final AdministrativoService administrativoService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdministrativoDTO.Response> cadastrar(
            @RequestBody @Valid AdministrativoDTO.CreateRequest req) {
        AdministrativoDTO.Response response = administrativoService.cadastrar(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDENADOR_DO_CURSO')")
    public ResponseEntity<Page<AdministrativoDTO.ListResponse>> listar(
            @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(administrativoService.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdministrativoDTO.Response> buscarPorId(@PathVariable Long id) {
        AdministrativoDTO.Response response = administrativoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<AdministrativoDTO.Response> atualizar(
//            @PathVariable Long id, @RequestBody @Valid AdministrativoDTO.UpdateRequest request) {
//        ProfessorDTO.Response response = administrativoService.atualizar(id, request);
//        return ResponseEntity.ok(response);
//    }
}
