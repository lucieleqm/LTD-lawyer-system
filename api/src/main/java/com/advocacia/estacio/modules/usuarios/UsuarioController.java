package com.advocacia.estacio.modules.usuarios;

import com.advocacia.estacio.infra.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping("/me")
    public ResponseEntity<UsuarioDTO.Response> buscarUsuarioLogado(
            @AuthenticationPrincipal CustomUserDetails usuarioLogado) {
        UsuarioDTO.Response response = usuarioService.buscarPorId(usuarioLogado.getId());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> alterarStatus(@PathVariable Long id, @RequestBody UsuarioStatus usuarioStatus) {
        usuarioService.alterarStatus(id, usuarioStatus);
        return ResponseEntity.noContent().build();
    }

    // TODO: redefinir senha
}
