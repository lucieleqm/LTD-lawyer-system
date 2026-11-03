package com.advocacia.estacio.modules.judicial.orgaos_julgadores;

import com.advocacia.estacio.modules.judicial.tribunais.Tribunal;
import com.advocacia.estacio.shared.entity.EntidadeBase;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "tbl_orgao_julgador")
public class OrgaoJulgador extends EntidadeBase {

    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tribunal_id")
    private Tribunal tribunal;

    private Integer instancia;

    protected OrgaoJulgador() {
    }

    public void atualizar(String nome, Tribunal tribunal, Integer instancia) {
        this.nome = nome;
        this.tribunal = tribunal;
        this.instancia = instancia;
    }

    @Builder
    public OrgaoJulgador(String nome, Tribunal tribunal, Integer instancia) {
        this.nome = nome;
        this.tribunal = tribunal;
        this.instancia = instancia;
    }
}
