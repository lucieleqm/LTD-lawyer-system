package com.advocacia.estacio.modules.membros.estagiarios;

import com.advocacia.estacio.modules.identidade.usuarios.UsuarioStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

interface EstagiarioRepository extends JpaRepository<Estagiario, Long>, JpaSpecificationExecutor<Estagiario> {

    @EntityGraph(attributePaths = {"pessoa", "pessoa.usuario"})
    @Query("SELECT e FROM Estagiario e WHERE e.id = :id")
    Optional<Estagiario> buscarDetalhesPorId(@Param("id") Long id);

    @Query("""
        SELECT new com.advocacia.estacio.modules.membros.estagiarios.EstagiarioDTO$OptionResponse(e.id, p.nome)
        FROM Estagiario e
        JOIN e.pessoa p
        JOIN p.usuario u
        WHERE (:nome IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
        AND u.status IN :status
    """)
    List<EstagiarioDTO.OptionResponse> buscarAtivosPorNome(
            @Param("nome") String nome,
            @Param("status") List<UsuarioStatus> status,
            Pageable pageable
    );
}
