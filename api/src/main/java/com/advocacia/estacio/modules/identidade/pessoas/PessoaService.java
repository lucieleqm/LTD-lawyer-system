package com.advocacia.estacio.modules.identidade.pessoas;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PessoaService {
    private final PessoaRepository pessoaRepository;

    public Pessoa obterReferencia(Long pessoaId) {
        return pessoaRepository.getReferenceById(pessoaId);
    }
}
