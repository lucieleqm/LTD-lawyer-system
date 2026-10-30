package com.advocacia.estacio.modules.pessoas;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    @EntityGraph(attributePaths = {"usuario", "endereco"})
    @Query("SELECT pess FROM Pessoa pess WHERE pess.id = :id")
    Optional<Pessoa> buscarDetalhesPorId(@Param("id") Long id);
}
