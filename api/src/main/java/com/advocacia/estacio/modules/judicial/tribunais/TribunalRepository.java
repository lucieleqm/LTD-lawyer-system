package com.advocacia.estacio.modules.judicial.tribunais;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TribunalRepository extends JpaRepository<Tribunal, Long>, JpaSpecificationExecutor<Tribunal> {

    Slice<Tribunal> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    boolean existsByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id);
}
