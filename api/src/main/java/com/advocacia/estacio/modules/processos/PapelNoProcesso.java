package com.advocacia.estacio.modules.processos;

import lombok.Getter;

@Getter
public enum PapelNoProcesso {
    AUTOR_REQUERENTE("Autor / Requerente"),
    REU_REQUERIDO("Réu / Requerido"),
    TESTEMUNHA("Testemunha"),
    JUIZ("Juiz"),
    LITISCONSORTE_ATIVO("Litisconsorte Ativo"),
    LITISCONSORTE_PASSIVO("Litisconsorte Passivo"),
    TERCEIRO_INTERESSADO("Terceiro Interessado"),
    MINISTERIO_PUBLICO("Ministério Público"),
    OUTROS("Outros");

    private final String descricao;

    PapelNoProcesso(String descricao) {
        this.descricao = descricao;
    }
}
