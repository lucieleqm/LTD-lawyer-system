package com.advocacia.estacio.modules.advogados;

import java.time.LocalDate;
import com.advocacia.estacio.modules.pessoas.Pessoa;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "tbl_advogado")
public class Advogado  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	@MapsId
	@JoinColumn(name = "pessoa_id", unique = true, nullable = false)
	private Pessoa pessoa;

	protected Advogado() {
	}

	public void atualizarDados(String nome, String telefone, LocalDate dataNascimento) {
		this.pessoa.atualizarDados(nome, telefone, dataNascimento);
	}

	public void desativar() {
		this.pessoa.desativarAcesso();
	}

	public void reativar() {
		this.pessoa.reativarAcesso();
	}

	// TODO: Adicionar hashCode() e equals()

//	@Override
//	public int hashCode() {
//		return Objects.hash(endereco, id);
//	}

//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (o == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Advogado other = (Advogado) obj;
//		return Objects.equals(dataDeNascimeto, other.dataDeNascimeto) && Objects.equals(email, other.email)
//				&& Objects.equals(endereco, other.endereco) && Objects.equals(id, other.id)
//				&& Objects.equals(nome, other.nome) && Objects.equals(registro, other.registro)
//				&& Objects.equals(telefone, other.telefone);
//	}

	@Builder
	public Advogado(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
}
