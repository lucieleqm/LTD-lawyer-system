package com.advocacia.estacio.modules.professores;

import com.advocacia.estacio.modules.pessoas.Pessoa;
import com.advocacia.estacio.modules.pessoas.PessoaDTO;
import com.advocacia.estacio.modules.usuarios.Usuario;
import com.advocacia.estacio.modules.usuarios.UsuarioDTO;
import com.advocacia.estacio.modules.usuarios.UsuarioStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public interface ProfessorDTO {
    @Schema(name = "ProfessorCreateRequest")
    record CreateRequest(
            @NotBlank(message = "O nome é obrigatório") String nome,
            @NotBlank(message = "O email é obrigatório")
            @Email(message = "Informe um formato de email válido") String email,
            @NotBlank(message = "A senha é obrigatória") String senha,
            @Pattern(regexp = "^\\d{8,11}$", message = "Informe um número de telefone válido") String telefone
    ) {
        public Professor toEntity(Usuario usuario) {
            Pessoa pessoa = Pessoa.builder()
                    .nome(nome)
                    .email(email)
                    .telefone(telefone)
                    .build();

            pessoa.vincularUsuario(usuario);

            return Professor.builder()
                    .pessoa(pessoa)
                    .build();
        }
    }

    @Schema(name = "ProfessorResponse")
    record Response(
            Long id,
            PessoaDTO.Response pessoa,
            UsuarioDTO.Response usuario
    ) {
        public Response (Professor professor) {
            this(
                    professor.getId(),
                    new PessoaDTO.Response( professor.getPessoa()),
                    new UsuarioDTO.Response(professor.getPessoa().getUsuario())
            );
        }
    }

    @Schema(name = "ProfessorListResponse")
    record ListResponse(
            Long id,
            String nome,
            String email,
            String telefone,
            UsuarioStatus usuarioStatus
    ) {
        public ListResponse(Professor professor) {
            this(
                    professor.getId(),
                    professor.getPessoa().getNome(),
                    professor.getPessoa().getEmail(),
                    professor.getPessoa().getTelefone(),
                    professor.getPessoa().getUsuario().getStatus()
            );
        }
    }

    @Schema(name = "ProfessorSearchFilter")
    record SearchFilter(
            String nome,
            UsuarioStatus usuarioStatus){}

    @Schema(name = "ProfessorOptionResponse")
    record OptionResponse(
            Long id,
            String nome
    ) {}

    @Schema(name = "ProfessorUpdateRequest")
    record UpdateRequest(
            String nome,
            @Pattern(regexp = "^\\d{8,11}$", message = "Informe um número de telefone válido") String telefone
    ) {}
}
