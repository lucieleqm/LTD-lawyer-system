package com.advocacia.estacio.modules.demandas;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;

// estado/posse
@RequiredArgsConstructor
//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EtapaDemanda {

	ELABORACAO("Em Elaboração pelo Estagiário"),
	REVISAO_PROFESSOR("Em Revisão pelo Professor"),
	VALIDACAO_ADVOGADO("Aguardando Validação do Advogado"),
	PROTOCOLADA("Protocolada"),
	ARQUIVADA("Arquivada");

	public boolean isFinalizada() {
		return this == PROTOCOLADA || this == ARQUIVADA;
	}

	private final String descricao;

	public String getDescricao() {
		return descricao;
	}

	public static String obterDescricao(EtapaDemanda etapaDemanda) {
		if (etapaDemanda == null) {
			return null;
		}
		return etapaDemanda.getDescricao();
	}
}
