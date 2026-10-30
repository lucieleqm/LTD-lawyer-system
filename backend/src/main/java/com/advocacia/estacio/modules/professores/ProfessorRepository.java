package com.advocacia.estacio.modules.professores;

import com.advocacia.estacio.modules.usuarios.UsuarioStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

interface ProfessorRepository extends JpaRepository<Professor, Long> {
    @EntityGraph(attributePaths = {"pessoa", "pessoa.usuario"})
    @Query("SELECT prof FROM Professor prof WHERE prof.id = :id")
    Optional<Professor> buscarDetalhesPorId(@Param("id") Long id);

    @EntityGraph(attributePaths = {"pessoa", "pessoa.usuario"})
    @Query("""
        SELECT pr FROM Professor pr
        JOIN pr.pessoa p
        JOIN p.usuario u
        WHERE (:nome IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
        AND (:status IS NULL OR u.status = :status)
    """)
    Page<Professor> buscarComFiltros(
            @Param("nome") String nome,
            @Param("status") UsuarioStatus status,
            Pageable pageable
    );

    @Query("""
        SELECT new com.advocacia.estacio.modules.professores.ProfessorDTO$OptionResponse(pr.id, p.nome)
        FROM Professor pr
        JOIN pr.pessoa p
        JOIN p.usuario u
        WHERE (:nome IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
        AND u.status IN :status
    """)
    List<ProfessorDTO.OptionResponse> buscarAtivosPorNome(
            @Param("nome") String nome,
            @Param("status") List<UsuarioStatus> status,
            Pageable pageable
    );
}
