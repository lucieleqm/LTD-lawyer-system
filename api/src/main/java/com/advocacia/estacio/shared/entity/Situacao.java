package com.advocacia.estacio.shared.entity;

public enum Situacao {
    ATIVOS(true), ARQUIVADOS(false), TODOS(null);

    private final Boolean valor;
    Situacao(Boolean valor) { this.valor = valor; }

    public Boolean valor() { return valor; }
}
