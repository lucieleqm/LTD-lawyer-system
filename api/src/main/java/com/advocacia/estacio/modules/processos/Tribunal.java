package com.advocacia.estacio.modules.processos;

public enum Tribunal {

//	ESTADUAL( "Estadual"),
//	FEDERAL( "Federal"),
//	TRABALHO("Trabalho");

    STF("Supremo Tribunal Federal"),
    STJ("Superior Tribunal de Justiça"),
    TJMA("Tribunal de Justiça do Maranhão"),
    TRF1("Tribunal Regional Federal da 1ª Região"),
    TRT16("Tribunal Regional do Trabalho da 16ª Região");

    private final String descricao;

    Tribunal(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static String obterDescricao(Tribunal tribunal) {
        if (tribunal == null) {
            return null;
        }
        return tribunal.getDescricao();
    }
}