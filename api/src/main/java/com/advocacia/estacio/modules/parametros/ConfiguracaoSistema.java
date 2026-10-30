package com.advocacia.estacio.modules.parametros;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "tbl_configuracao_sistema")
public class ConfiguracaoSistema {
    @Id
    private Long id = 1L;

    private LocalDate inicioFeriasColetivas;
    private LocalDate fimFeriasColetivas;
}
