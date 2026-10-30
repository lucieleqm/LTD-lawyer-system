package com.advocacia.estacio.modules.demandas;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

interface DemandaRepository extends JpaRepository<Demanda, Long>, JpaSpecificationExecutor<Demanda> {

    @EntityGraph(attributePaths = {
            "advogado.pessoa",
            "estagiario.pessoa",
            "professor.pessoa"
    })
    @Query("SELECT d FROM Demanda d WHERE d.id = :id")
    Optional<Demanda> buscarDetalhesPorId(@Param("id") Long id);


//	@Query("""
//		SELECT DISTINCT d FROM Demanda d
//			JOIN d.movimentacoes a
//				WHERE a.autor.id = :pessoaId
//	""")
//	Page<Demanda> buscarDemandasPorPessoa(
//			@Param("pessoaId") Long pessoaId, Pageable pageable
//	);
//
//	Page<Demanda> buscarTodosPorStatus(
//			@Param("demandaStatus") EtapaDemanda etapaDemanda, Pageable pageable);
}
