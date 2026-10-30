package com.advocacia.estacio.modules.demandas;

public enum Tempestividade {

	DENTRO_DO_PRAZO("Dentro do Prazo"),
	FORA_DO_PRAZO("Fora do Prazo");

	private final String descricao;

	Tempestividade(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public static String obterDescricao(Tempestividade tempestividade) {
		if (tempestividade == null) {
			return null;
		}
		return tempestividade.getDescricao();
	}
}
