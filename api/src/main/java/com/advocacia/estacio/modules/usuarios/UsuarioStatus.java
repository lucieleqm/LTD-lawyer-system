package com.advocacia.estacio.modules.usuarios;

public enum UsuarioStatus {

	ATIVO( "Ativo"),
	INATIVO( "Inativo"),
	BLOQUEADO("Bloqueado");

	private final String descricao;

	UsuarioStatus(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

    public static String obterDescricao(UsuarioStatus usuarioStatus) {
        if (usuarioStatus == null) {
            return null;
        }
        return usuarioStatus.getDescricao();
    }
}
