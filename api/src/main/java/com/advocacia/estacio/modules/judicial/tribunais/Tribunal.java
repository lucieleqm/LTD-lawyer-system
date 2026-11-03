package com.advocacia.estacio.modules.judicial.tribunais;

import com.advocacia.estacio.shared.entity.EntidadeBase;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "tbl_tribunal")
public class Tribunal extends EntidadeBase {

    private String sigla;

    private String nome;

    @Enumerated(EnumType.STRING)
    private EsferaJustica esfera;

    protected Tribunal() {
    }

    public void atualizar(String sigla, String nome, EsferaJustica esfera) {
        this.sigla = sigla;
        this.nome = nome;
        this.esfera = esfera;
    }

    @Builder
    public Tribunal( String sigla, String nome, EsferaJustica esfera) {
        this.sigla = sigla;
        this.nome = nome;
        this.esfera = esfera;
    }
}
