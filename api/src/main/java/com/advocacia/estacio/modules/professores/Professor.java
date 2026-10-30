package com.advocacia.estacio.modules.professores;

import com.advocacia.estacio.modules.pessoas.Pessoa;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;


@Entity
@Getter
@Table(name = "tbl_professor")
public class Professor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	@MapsId
	@JoinColumn(name = "pessoa_id", unique = true, nullable = false)
	private Pessoa pessoa;

	protected Professor() {
	}

	public void atualizarDados(String nome, String telefone) {
		this.pessoa.atualizarDados(nome, telefone, null);
	}

	// TODO: falta hashCode() e equals

	@Builder
	public Professor(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
}
