package com.advocacia.estacio.modules.pessoas;

public enum EstadoCivil {
	SOLTEIRO ("Solteiro(a)"),
	CASADO("Casado(a)"),
	DIVORCIADO("Divorciado(a)"),
	VIUVO("Viuvo(a)"),
	SEPARADO_JUDICIALMENTE("Separado Judicialmente");
	
	private final String descricao;

	EstadoCivil(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public static String obterDescricao(EstadoCivil estadoCivil) {
		if (estadoCivil == null) {
			return null;
		}
		return estadoCivil.getDescricao();
	}
}
