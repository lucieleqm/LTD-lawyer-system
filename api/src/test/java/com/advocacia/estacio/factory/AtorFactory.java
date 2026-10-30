package com.advocacia.estacio.factory;

import com.advocacia.estacio.domain.entities.Ator;
import com.advocacia.estacio.modules.professores.Professor;

public abstract class AtorFactory {

	public static Ator criarAtor(String tipo) {
		return switch(tipo.toLowerCase()) {
			case "coordenador do curso" -> new CoordenadorDoCurso();
			case "secretário" -> new Secretario();
			case "professor" -> new Professor();
			default -> throw new IllegalArgumentException("Tipo de ator inválido! " + tipo);
		};
	}
}
