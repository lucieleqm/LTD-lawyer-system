package com.advocacia.estacio.modules.pessoas;

public interface PessoaDTO {
    // retorna apenas os atributos que são universais, todos possuem.
    record Response(
            Long id,
            String nome,
            String email,
            String telefone
    ) {
        public Response(Pessoa pessoa) {
            this(pessoa.getId(), pessoa.getNome(), pessoa.getEmail(), pessoa.getTelefone());
        }
    }
}
