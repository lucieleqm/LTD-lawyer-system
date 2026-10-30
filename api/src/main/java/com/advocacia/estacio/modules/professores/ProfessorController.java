package com.advocacia.estacio.modules.professores;

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
@RequestMapping("/api/v1/professores")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    @PostMapping
    public ResponseEntity<ProfessorDTO.Response> cadastrar(
            @RequestBody @Valid ProfessorDTO.CreateRequest request, UriComponentsBuilder uriBuilder) {
        ProfessorDTO.Response response = professorService.cadastrar(request);
        var uri = uriBuilder.path("/api/v1/professores/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ProfessorDTO.ListResponse>> listar(
            @RequestParam(required = false) ProfessorDTO.SearchFilter filtro,
            @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        var filtroSeguro = (filtro != null) ? filtro : new ProfessorDTO.SearchFilter(null, null);
        var page = professorService.listar(filtroSeguro, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/opcoes")
    public ResponseEntity<List<ProfessorDTO.OptionResponse>> listarOpcoes(
            @RequestParam(defaultValue = "") String nome) {
        var response = professorService.listarOpcoes(nome);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDTO.Response> buscarPorId(@PathVariable Long id) {
        ProfessorDTO.Response response = professorService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDTO.Response> atualizar(
            @PathVariable Long id, @RequestBody @Valid ProfessorDTO.UpdateRequest request) {
        ProfessorDTO.Response response = professorService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }
}
