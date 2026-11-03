package com.advocacia.estacio.modules.judicial.tribunais;

import lombok.Getter;

@Getter
public enum EsferaJustica {
    ESTADUAL("Estadual"),
    FEDERAL("Federal"),
    TRABALHISTA("Trabalhista"),
    ELEITORAL("Eleitoral");

    private final String descricao;

    EsferaJustica(String descricao) {
        this.descricao = descricao;
    }
}
