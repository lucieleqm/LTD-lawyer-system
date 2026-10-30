package com.advocacia.estacio.modules.processos;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProcessoRepository extends JpaRepository<Processo, Long>, JpaSpecificationExecutor<Processo> {

    @EntityGraph(attributePaths = {
            "advogado.pessoa",
            "estagiario.pessoa",
            "assistido.pessoa",
            "partesEnvolvidas"
    })
    @Query("SELECT p FROM Processo p WHERE p.id = :id")
    Optional<Processo> buscarDetalhesPorId(@Param("id") Long id);

    @Query(value = "SELECT NEXT VALUE FOR seq_numero_processo", nativeQuery = true)
    Long obterProximoSequencial();

    boolean existsByNumeroProcessoPje(String numeroPje);
}
