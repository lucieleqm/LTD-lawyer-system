package com.advocacia.estacio.modules.parametros;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfigSistemaRepository extends JpaRepository<ConfiguracaoSistema, Long> {
    Optional<ConfiguracaoSistema> findById(long id);
}
