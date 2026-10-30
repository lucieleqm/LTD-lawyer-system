package com.advocacia.estacio.modules.pessoas.enderecos;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;


@Entity
@Getter
@Table(name = "tbl_endereco")
public class Endereco implements Serializable {
    //    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 9)
    private String cep;

    @Column(nullable = false, length = 255)
    private String logradouro;

    @Column(name = "numero", length = 12)
    private String numero;

    @Column(length = 150)
    private String complemento;

    @Column(length = 100)
    private String bairro;

    @Column(length = 100)
    private String cidade;

    protected Endereco() {
    }

    public void atualizarDados(String cep, String logradouro, String numero, String complemento, String bairro, String cidade) {
        if (cep != null && !cep.isBlank()) this.cep = cep;
        if (logradouro != null && !logradouro.isBlank()) this.logradouro = logradouro;
        if (numero != null && !numero.isBlank()) this.numero = numero;
        if (complemento != null) this.complemento = complemento;
        if (bairro != null && !bairro.isBlank()) this.bairro = bairro;
        if (cidade != null && !cidade.isBlank()) this.cidade = cidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Endereco outro)) return false;
        return id != null && id.equals(outro.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Builder
    public Endereco(String cep, String logradouro, String numero, String complemento, String bairro, String cidade) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
    }
}
