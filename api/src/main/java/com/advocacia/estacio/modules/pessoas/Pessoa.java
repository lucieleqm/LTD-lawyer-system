package com.advocacia.estacio.modules.pessoas;

import com.advocacia.estacio.modules.pessoas.enderecos.Endereco;
import com.advocacia.estacio.modules.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Table(name = "tbl_pessoa", indexes = {
        @Index(name = "idx_pessoa_nome", columnList = "nome")
})
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @Column(nullable = false)
    private String nome;

    // TODO: para um 'Assistido' nao faria sentido e email ser único
    @Column(unique = true)
    // TODO: add
    //  @Column(nullable = false, unique = true, updatable = false)
    //  protecao extra caso for usada no hash
    private String email;

    @Column(length = 20)
    private String telefone;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "criado_em", updatable = false)
    @CreationTimestamp
    private LocalDateTime criadoEm;

    protected Pessoa() {
    }

    // por ta sendo utilizado no hash e tbm existir um email em 'Usuario', o email em 'Pessoa' n é atualizável
    // TODO: encontrar uma solucao p/ modificar essa regra de negócio
    public void atualizarDados(String nome, String telefone, LocalDate dataNascimento) {
        if (nome != null && !nome.isBlank()) {
            this.nome = nome;
        }
        if (telefone != null && !telefone.isBlank()) {
            this.telefone = telefone;
        }
        if (dataNascimento != null) {
            this.dataNascimento = dataNascimento;
        }
    }

    public void vincularEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void vincularUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (usuario != null) {
            usuario.setPessoa(this);
        }
    }

    public void desativarAcesso() {
        if (this.usuario != null) {
            this.usuario.desativar();
        }
    }

    public void reativarAcesso() {
        if (this.usuario != null) {
            this.usuario.reativar();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pessoa pessoa)) return false;
        return email != null && Objects.equals(email, pessoa.getEmail());
    }

    @Override
    public int hashCode() {
        // usar Objects.hash() se tiver mais um campo.
        return Objects.hashCode(email);
    }

    @Builder
    public Pessoa(Endereco endereco, String nome, String email, String telefone, LocalDate dataNascimento) {
        this.endereco = endereco;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
    }
}
