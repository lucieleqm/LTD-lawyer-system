package com.advocacia.estacio.modules.assistidos;

import com.advocacia.estacio.modules.pessoas.Pessoa;
import com.advocacia.estacio.modules.pessoas.EstadoCivil;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "tbl_assistido")
public class Assistido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
	@MapsId
	@JoinColumn(name = "pessoa_id", unique = true, nullable = false)
	private Pessoa pessoa;

	// TODO: ela é única?
	@Column(length = 50)
	private String matricula;

	@Column(length = 100)
	private String profissao;

	@Column(length = 50)
	private String nacionalidade;

	@Column(length = 50)
	private String naturalidade;
	
	@Enumerated(EnumType.STRING)
	private EstadoCivil estadoCivil;
	
	protected Assistido() {
	}

	public void atualizarDados(String nome, String telefone, LocalDate dataNascimento, String matricula, String profissao,
							   String nacionalidade, String naturalidade, EstadoCivil estadoCivil) {

		this.pessoa.atualizarDados(nome, telefone, dataNascimento);

		if (matricula != null && !matricula.isBlank()) {
			this.matricula = matricula;
		}
		if (profissao != null && !profissao.isBlank()) {
			this.profissao = profissao;
		}
		if (nacionalidade != null && !nacionalidade.isBlank()) {
			this.nacionalidade = nacionalidade;
		}
		if (naturalidade != null && !naturalidade.isBlank()) {
			this.naturalidade = naturalidade;
		}
		if (estadoCivil != null) {
			this.estadoCivil = estadoCivil;
		}
	}

	// TODO: falta fazer hashCode() e equals()
//	@Override
//	public int hashCode() {
//		return Objects.hash(matricula);
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Assistido other = (Assistido) obj;
//		return Objects.equals(email, other.email) && Objects.equals(endereco, other.endereco)
//				&& Objects.equals(id, other.id) && Objects.equals(matricula, other.matricula)
//				&& Objects.equals(nome, other.nome) && Objects.equals(telefone, other.telefone);
//	}

	@Builder
	public Assistido(Pessoa pessoa, String matricula, String profissao, String nacionalidade,
					 String naturalidade, EstadoCivil estadoCivil) {
		this.pessoa = pessoa;
		this.matricula = matricula;
		this.profissao = profissao;
		this.nacionalidade = nacionalidade;
		this.naturalidade = naturalidade;
		this.estadoCivil = estadoCivil;
	}
}
