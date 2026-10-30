package com.advocacia.estacio.modules.processos;

public enum AreaDireito {
	
	CIVIL( "Direito Civil"),
	TRABALHISTA("Direito Trabalhista"),
	PREVIDENCIARIO("Direito Previdenciário");

//	TODO: Verificar necessidade da adição das demais áreas
//	FAMILIA_E_SUCESSOES("Direito de Família e Sucessões"),
//	CONSUMIDOR("Direito do Consumidor"),
//	PENAL("Direito Penal"),
//	TRIBUTARIO("Direito Tributário"),
//	EMPRESARIAL("Direito Empresarial"),
//	ADMINISTRATIVO("Direito Administrativo"),
//	CONSTITUCIONAL("Direito Constitucional"),
//	DIGITAL("Direito Digital"),
//	AMBIENTAL("Direito Ambiental"),
//	ELEITORAL("Direito Eleitoral"),
//	INTERNACIONAL("Direito Internacional");

	private final String descricao;

	AreaDireito(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public static String obterDescricao(AreaDireito areaDireito) {
		if (areaDireito == null) {
			return null;
		}
		return areaDireito.getDescricao();
	}
}
