package com.advocacia.estacio.modules.demandas.tramitacoes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandaTramitacaoRepository extends JpaRepository<DemandaTramitacao, Long> {
    @EntityGraph(attributePaths = {"responsavel"})
    Page<DemandaTramitacao> findByDemandaId(Long demandaId, Pageable pageable);
}
