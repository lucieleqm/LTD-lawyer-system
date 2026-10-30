package com.advocacia.estacio.modules.advogados;

import com.advocacia.estacio.modules.pessoas.Pessoa;
import com.advocacia.estacio.modules.pessoas.PessoaDTO;
import com.advocacia.estacio.modules.pessoas.enderecos.Endereco;
import com.advocacia.estacio.modules.pessoas.enderecos.EnderecoDTO;
import com.advocacia.estacio.modules.usuarios.Usuario;
import com.advocacia.estacio.modules.usuarios.UsuarioDTO;
import com.advocacia.estacio.modules.usuarios.UsuarioStatus;
import com.advocacia.estacio.shared.validations.MaiorDeIdade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public interface AdvogadoDTO {
    @Schema(name = "AdvogadoCreateRequest")
    record CreateRequest(
            @NotBlank(message = "O nome é obrigatório") String nome,
            @NotBlank(message = "O email é obrigatório")
            @Email(message = "Informe um formato de email válido") String email,
            @NotBlank(message = "A senha é obrigatória") String senha,
            @Pattern(regexp = "^\\d{8,11}$", message = "Informe um número de telefone válido") String telefone,
            @Past(message = "A data de nascimento deve estar no passado")
            @MaiorDeIdade(message = "O advogado deve ter no mínimo 18 anos") LocalDate dataNascimento,
            @Valid EnderecoDTO.Request endereco
    ) {
        public Advogado toEntity(Usuario usuario, Endereco endereco) {
            Pessoa pessoa = Pessoa.builder()
                    .nome(nome)
                    .email(email)
                    .telefone(telefone)
                    .dataNascimento(dataNascimento)
                    .endereco(endereco)
                    .build();

            pessoa.vincularUsuario(usuario);

            return Advogado.builder()
                    .pessoa(pessoa)
                    .build();
        }
    }

    @Schema(name = "AdvogadoResponse")
    record Response(
            Long id,
            LocalDate dataNascimento,
            EnderecoDTO.Response endereco,
            PessoaDTO.Response pessoa,
            UsuarioDTO.Response usuario
    ) {
        public Response(Advogado advogado) {
            this(
                    advogado.getId(),
                    advogado.getPessoa().getDataNascimento(),
                    EnderecoDTO.Response.from(advogado.getPessoa().getEndereco()),
                    new PessoaDTO.Response(advogado.getPessoa()),
                    new UsuarioDTO.Response(advogado.getPessoa().getUsuario())
            );
        }
    }

    @Schema(name = "AdvogadoListResponse")
    record ListResponse(
            Long id,
            String nome,
            String email,
            String telefone,
            UsuarioStatus usuarioStatus
    ) {
        public ListResponse(Advogado advogado) {
            this(
                    advogado.getId(),
                    advogado.getPessoa().getNome(),
                    advogado.getPessoa().getEmail(),
                    advogado.getPessoa().getTelefone(),
                    advogado.getPessoa().getUsuario().getStatus()
            );
        }
    }

    @Schema(name = "AdvogadoSearchFilter")
    record SearchFilter(
            String nome,
            UsuarioStatus usuarioStatus) {
    }

    @Schema(name = "AdvogadoOptionResponse")
    record OptionResponse(
            Long id,
            String nome
    ) {
    }

    @Schema(name = "AdvogadoUpdateRequest")
    record UpdateRequest(
            String nome,
            @Pattern(regexp = "^\\d{8,11}$", message = "Informe um número de telefone válido") String telefone,
            @Past(message = "A data de nascimento deve estar no passado")
            @MaiorDeIdade(message = "O advogado deve ter no mínimo 18 anos") LocalDate dataNascimento,
            @Valid EnderecoDTO.Request endereco
    ) {}
}
