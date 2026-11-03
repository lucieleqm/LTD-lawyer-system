package com.advocacia.estacio.modules.judicial.areas_direito;

import com.advocacia.estacio.shared.entity.EntidadeBase;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "tbl_area_direito", indexes = {
        @Index(name = "idx_area_direito_nome", columnList = "nome")
})
public class AreaDireito extends EntidadeBase {

    @Column(nullable = false, length = 120)
    private String nome;

    protected AreaDireito() {
    }

    public void atualizar(String nome) {
        this.nome = nome;
    }

    @Builder
    public AreaDireito(String nome) {
        this.nome = nome;
    }
}
