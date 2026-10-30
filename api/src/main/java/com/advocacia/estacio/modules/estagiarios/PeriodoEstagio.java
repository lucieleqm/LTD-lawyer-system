package com.advocacia.estacio.modules.estagiarios;

public enum PeriodoEstagio {
	ESTAGIO_I("Estágio I"),
	ESTAGIO_II("Estágio II"),
	ESTAGIO_III("Estágio III"),
	ESTAGIO_IV("Estágio IV");

	private final String descricao;

	PeriodoEstagio(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public static String obterDescricao(PeriodoEstagio periodoEstagio) {
		if (periodoEstagio == null) {
			return null;
		}
		return periodoEstagio.getDescricao();
	}
}
