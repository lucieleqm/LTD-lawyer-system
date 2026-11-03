package com.advocacia.estacio.modules.judicial.areas_direito;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface AreaDireitoRepository extends JpaRepository<AreaDireito, Long>, JpaSpecificationExecutor<AreaDireito> {

    Slice<AreaDireito> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    boolean existsByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id);
}
