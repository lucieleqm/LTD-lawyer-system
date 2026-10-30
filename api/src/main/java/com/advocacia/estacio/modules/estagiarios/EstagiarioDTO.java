package com.advocacia.estacio.modules.estagiarios;

import com.advocacia.estacio.modules.pessoas.Pessoa;
import com.advocacia.estacio.modules.pessoas.PessoaDTO;
import com.advocacia.estacio.modules.usuarios.Usuario;
import com.advocacia.estacio.modules.usuarios.UsuarioDTO;
import com.advocacia.estacio.modules.usuarios.UsuarioStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public interface EstagiarioDTO {
    @Schema(name = "EstagiarioCreateRequest")
    record CreateRequest(
            @NotBlank(message = "O nome é obrigatório") String nome,
            @NotBlank(message = "O email é obrigatório")
            @Email(message = "Informe um formato de email válido") String email,
            @NotBlank(message = "A senha é obrigatória") String senha,
            @Pattern(regexp = "^\\d{8,11}$", message = "Informe um número de telefone válido") String telefone,
            @NotBlank(message = "A matrícula é obrigatória") String matricula,
            @NotNull(message = "O período do estágio é obrigatório") PeriodoEstagio periodoEstagio
    ) {
        public Estagiario toEntity(Usuario usuario) {
            Pessoa pessoa = Pessoa.builder()
                    .nome(this.nome)
                    .email(this.email)
                    .telefone(this.telefone)
                    .build();

            pessoa.vincularUsuario(usuario);

            return Estagiario.builder()
                    .pessoa(pessoa)
                    .matricula(this.matricula)
                    .periodo(this.periodoEstagio)
                    .build();
        }
    }

    @Schema(name = "EstagiarioResponse")
    record Response(
            Long id,
            String matricula,
            PeriodoEstagio periodoEstagio,
            PessoaDTO.Response pessoa,
            UsuarioDTO.Response usuario
    ) {
        public Response(Estagiario estagiario) {
            this(
                    estagiario.getId(),
                    estagiario.getMatricula(),
                    estagiario.getPeriodo(),
                    new PessoaDTO.Response(estagiario.getPessoa()),
                    new UsuarioDTO.Response(estagiario.getPessoa().getUsuario())
            );
        }
    }

    @Schema(name = "EstagiarioListResponse")
    record ListResponse(
            Long id,
            String matricula,
            String nome,
            PeriodoEstagio periodoEstagio,
            UsuarioStatus usuarioStatus
    ) {
        public ListResponse(Estagiario estagiario) {
            this(
                    estagiario.getId(),
                    estagiario.getMatricula(),
                    estagiario.getPessoa().getNome(),
                    estagiario.getPeriodo(),
                    estagiario.getPessoa().getUsuario().getStatus()
            );
        }
    }

    @Schema(name = "EstagiarioOptionResponse")
    record OptionResponse(
            Long id,
            String nome
    ) {}

    @Schema(name = "EstagiarioSearchFilter")
    record SearchFilter(
            String termo,
            PeriodoEstagio periodoEstagio,
            UsuarioStatus usuarioStatus
    ) {}

    @Schema(name = "EstagiarioUpdateRequest")
    record UpdateRequest(
            String nome,
            @Pattern(regexp = "^\\d{8,11}$", message = "Informe um número de telefone válido") String telefone,
            String matricula,
            PeriodoEstagio periodoEstagio
    ) {}
}
