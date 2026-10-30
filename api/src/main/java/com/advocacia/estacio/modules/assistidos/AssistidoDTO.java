package com.advocacia.estacio.modules.assistidos;

import com.advocacia.estacio.modules.pessoas.EstadoCivil;
import com.advocacia.estacio.modules.pessoas.Pessoa;
import com.advocacia.estacio.modules.pessoas.PessoaDTO;
import com.advocacia.estacio.modules.pessoas.enderecos.Endereco;
import com.advocacia.estacio.modules.pessoas.enderecos.EnderecoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface AssistidoDTO {
    @Schema(name = "AssistidoCreateRequest")
    record CreateRequest(
            @NotBlank(message = "O nome é obrigatório") String nome,
            @Email(message = "Informe um formato de email válido") String email,
            @Pattern(regexp = "^\\d{8,11}$", message = "Informe um número de telefone válido") String telefone,
            String matricula,
            String profissao,
            String nacionalidade,
            String naturalidade,
            EstadoCivil estadoCivil,
            @Valid EnderecoDTO.Request endereco
    ) {
        public Assistido toEntity(Endereco endereco) {
            Pessoa pessoa = Pessoa.builder()
                    .nome(this.nome())
                    .email(this.email())
                    .telefone(this.telefone())
                    .endereco(endereco)
                    .build();

            return Assistido.builder()
                    .pessoa(pessoa)
                    .matricula(this.matricula())
                    .estadoCivil(this.estadoCivil())
                    .nacionalidade(this.nacionalidade())
                    .naturalidade(this.naturalidade())
                    .profissao(this.profissao())
                    .build();
        }
    }

    @Schema(name = "AssistidoResponse")
    record Response(
            Long id,
            String matricula,
            String profissao,
            String nacionalidade,
            String naturalidade,
            EstadoCivil estadoCivil,
            LocalDateTime criadoEm,
            EnderecoDTO.Response endereco,
            PessoaDTO.Response pessoa
    ) {
        public Response(Assistido assistido) {
            this(
                    assistido.getId(),
                    assistido.getMatricula(),
                    assistido.getProfissao(),
                    assistido.getNacionalidade(),
                    assistido.getNaturalidade(),
                    assistido.getEstadoCivil(),
                    assistido.getPessoa().getCriadoEm(),
                    EnderecoDTO.Response.from(assistido.getPessoa().getEndereco()),
                    new PessoaDTO.Response(assistido.getPessoa())
            );
        }
    }

    @Schema(name = "AssistidoListResponse")
    record ListResponse(
            Long id,
            String matricula,
            String nome,
            String telefone
    ) {
        public ListResponse (Assistido assistido){
            this(
                    assistido.getId(),
                    assistido.getMatricula(),
                    assistido.getPessoa().getNome(),
                    assistido.getPessoa().getTelefone()
            );
        }
    }

    @Schema(name = "AssistidoSearchFilter")
    record SearchFilter(
            String termo
    ){}

    @Schema(name = "AssistidoOptionResponse")
    record OptionResponse(
            Long id,
            String nome
    ) {}

    @Schema(name = "AssistidoUpdateRequest")
    record UpdateRequest(
            String nome,
            @Email(message = "Informe um formato de email válido") String email,
            @Pattern(regexp = "^\\d{8,11}$", message = "Informe um número de telefone válido") String telefone,
            LocalDate dataNascimento,
            String matricula,
            String profissao,
            String nacionalidade,
            String naturalidade,
            EstadoCivil estadoCivil,
            @Valid EnderecoDTO.Request endereco
    ) {}
}
