package com.advocacia.estacio.modules.pessoas.enderecos;

import io.swagger.v3.oas.annotations.media.Schema;

public interface EnderecoDTO {
    @Schema(name = "EnderecoRequest")
    record Request(
            String cep,
            String logradouro,
            String numero,
            String complemento,
            String bairro,
            String cidade
    ) {
        public Endereco toEntity() {
            return Endereco.builder()
                    .cep(cep)
                    .logradouro(logradouro)
                    .numero(numero)
                    .complemento(complemento)
                    .bairro(bairro)
                    .cidade(cidade)
                    .build();
        }
    }

    @Schema(name = "EnderecoResponse")
    record Response(
            Long id,
            String cep,
            String logradouro,
            String numero,
            String complemento,
            String bairro,
            String cidade
    ) {
        /**
         * Converte a entidade Endereco para este DTO de forma segura.
         * <p>
         * Use sempre este método em vez de criar o DTO direto. Ele evita o
         * erro de "NullPointerException" caso a pessoa ainda não tenha um
         * endereço cadastrado no banco de dados.
         *
         * @param endereco O endereço que veio do banco (pode ser null).
         * @return O DTO preenchido, ou null se não houver endereço.
         */
        public static Response from(Endereco endereco) {
            if (endereco == null) return null;
            return new Response(endereco);
        }

        private Response(Endereco endereco) {
            this(
                    endereco.getId(),
                    endereco.getCep(),
                    endereco.getLogradouro(),
                    endereco.getNumero(),
                    endereco.getComplemento(),
                    endereco.getBairro(),
                    endereco.getCidade()
            );
        }
    }
}
