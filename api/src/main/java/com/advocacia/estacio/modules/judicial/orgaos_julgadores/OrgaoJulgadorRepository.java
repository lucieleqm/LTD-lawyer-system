package com.advocacia.estacio.modules.judicial.orgaos_julgadores;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface OrgaoJulgadorRepository  extends JpaRepository<OrgaoJulgador, Long>, JpaSpecificationExecutor<OrgaoJulgador> {
    @EntityGraph(attributePaths = "tribunal")
    Page<OrgaoJulgador> findAll(Specification<OrgaoJulgador> spec, Pageable pageable);

    @EntityGraph(attributePaths = "tribunal")
    Optional<OrgaoJulgador> findById(Long id);

    Slice<OrgaoJulgador> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    boolean existsByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id);
}
