package com.advocacia.estacio.modules.usuarios;

import java.time.LocalDateTime;
import java.util.Objects;

import com.advocacia.estacio.modules.pessoas.Pessoa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Table(name = "tbl_usuario")
public class Usuario  {

	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "pessoa_id", unique = true)
	private Pessoa pessoa;

	@Column(nullable = false, unique = true, length = 100)
	private String login;

	@Column(nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private UsuarioRole role;

	@Setter
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UsuarioStatus status = UsuarioStatus.ATIVO;

	@Column(name = "precisa_trocar_senha", nullable = false)
	private boolean precisaTrocarSenha = true;

	@Column(name = "criado_em",  updatable = false)
	@CreationTimestamp
	private LocalDateTime criadoEm;

	@Column(name = "desativado_em")
	private LocalDateTime desativadoEm;

	@Column(name = "bloqueado_em")
	private LocalDateTime bloqueadoEm;

	protected Usuario() {
	}

	public Usuario(String login, String password, UsuarioRole role) {
		this.login = login;
		this.password = password;
		this.role = role;
	}

	public void desativar() {
		this.status = UsuarioStatus.INATIVO;
		this.desativadoEm = LocalDateTime.now();
		this.bloqueadoEm = null;
	}

	public void reativar() {
		this.status = UsuarioStatus.ATIVO;
		this.desativadoEm = null;
		this.bloqueadoEm = null;
	}

	public void bloquear() {
		this.status = UsuarioStatus.BLOQUEADO;
		this.bloqueadoEm = LocalDateTime.now();
		this.desativadoEm = null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(login);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(login, other.login);
	}
}
