package com.advocacia.estacio.modules.administrativo;

import com.advocacia.estacio.modules.pessoas.Pessoa;
import com.advocacia.estacio.modules.pessoas.PessoaDTO;
import com.advocacia.estacio.modules.usuarios.Usuario;
import com.advocacia.estacio.modules.usuarios.UsuarioDTO;
import com.advocacia.estacio.modules.usuarios.UsuarioRole;
import com.advocacia.estacio.modules.usuarios.UsuarioStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public interface AdministrativoDTO {
    @Schema(name = "AdministrativoCreateRequest")
    record CreateRequest(
            @NotBlank(message = "O nome é obrigatório") String nome,
            @Pattern(regexp = "^\\d{8,11}$", message = "Informe um número de telefone válido") String telefone,
            @NotBlank(message = "O email é obrigatório")
            @Email(message = "Informe um formato de email válido") String email,
            @NotBlank(message = "A senha é obrigatória") String senha,
            @NotNull(message = "O cargo é obrigatório") UsuarioRole role
    ) {
        public Pessoa toEntity(Usuario usuario) {
            Pessoa pessoa = Pessoa.builder()
                    .nome(this.nome)
                    .telefone(this.telefone)
                    .email(this.email)
                    .build();

            pessoa.vincularUsuario(usuario);
            return pessoa;
        }
    }

    @Schema(name = "AdministrativoResponse")
    record Response(
            PessoaDTO.Response pessoa,
            UsuarioDTO.Response usuario
    ) {
        public Response(Pessoa pessoa) {
            this(
                    new PessoaDTO.Response(pessoa),
                    pessoa.getUsuario() != null ? new UsuarioDTO.Response(pessoa.getUsuario()) : null
            );
        }
    }

    @Schema(name = "AdministrativoListResponse")
    record ListResponse(
            Long id,
            String nome,
            String login,
            UsuarioRole role,
            UsuarioStatus usuarioStatus
    ) {
        public ListResponse(Usuario usuario) {
            this(
                    usuario.getPessoa().getId(),
                    usuario.getPessoa().getNome(),
                    usuario.getLogin(),
                    usuario.getRole(),
                    usuario.getStatus()
            );
        }
    }

    @Schema(name = "AdministrativoUpdateRequest")
    record UpdateRequest(
            @NotBlank(message = "O nome é obrigatório") String nome,
            @Pattern(regexp = "^\\d{8,11}$", message = "Informe um número de telefone válido") String telefone
    ) {
    }
}
