package com.advocacia.estacio.shared.pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class Paginacao {
    public static final int TAMANHO_OPCOES = 20;
    public static final int TAMANHO_PADRAO = 20;
    public static final int TAMANHO_MAXIMO = 100;

    private Paginacao() {}

    /** Para endpoints de opções (selects/autocomplete): tamanho fixo. */
    public static Pageable opcoes(int page, Sort sort) {
        return PageRequest.of(Math.max(page, 0), TAMANHO_OPCOES, sort);
    }

    /** Para listagens comuns: respeita o size pedido, limitado ao máximo. */
    public static Pageable de(int page, int size, Sort sort) {
        int tamanho = Math.min(Math.max(size, 1), TAMANHO_MAXIMO);
        return PageRequest.of(Math.max(page, 0), tamanho, sort);
    }
}
