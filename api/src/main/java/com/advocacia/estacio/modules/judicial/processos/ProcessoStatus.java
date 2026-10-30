package com.advocacia.estacio.modules.judicial.processos;

import lombok.Getter;

@Getter
public enum ProcessoStatus {
	
	TRAMITANDO("Tramitando"),
	SUSPENSO("Suspenso"),
	ARQUIVADO("Arquivado"),
	TODOS("Todos");

	private final String descricao;

	ProcessoStatus(String descricao) { this.descricao = descricao; }
}
