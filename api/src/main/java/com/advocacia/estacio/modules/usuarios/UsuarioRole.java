package com.advocacia.estacio.modules.usuarios;

public enum UsuarioRole {
	ADMIN("admin"),
	COORDENADOR_DO_CURSO("Coordenador do curso"),
	SECRETARIO("Secretário"),
	PROFESSOR("Professor"),
	ESTAGIARIO("Estagiário"),
	ADVOGADO("Advogado");

	private final String descricao;

	UsuarioRole(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public boolean isAdministrativo() {
		return this == COORDENADOR_DO_CURSO || this == SECRETARIO;
	}

	public static String obterDescricao(UsuarioRole usuarioRole) {
		if (usuarioRole == null) {
			return null;
		}
		return usuarioRole.getDescricao();
	}
}
