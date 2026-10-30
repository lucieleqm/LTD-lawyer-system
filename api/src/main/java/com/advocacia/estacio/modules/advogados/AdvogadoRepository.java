package com.advocacia.estacio.modules.advogados;

import com.advocacia.estacio.modules.usuarios.UsuarioStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

interface AdvogadoRepository extends JpaRepository<Advogado, Long> {

    @EntityGraph(attributePaths = {"pessoa", "pessoa.endereco", "pessoa.usuario"})
    @Query("SELECT a FROM Advogado a WHERE a.id = :id")
    Optional<Advogado> buscarDetalhesPorId(@Param("id") Long id);

    @EntityGraph(attributePaths = {"pessoa", "pessoa.usuario"})
    @Query("""
        SELECT a FROM Advogado a
        JOIN a.pessoa p
        JOIN p.usuario u
        WHERE (:nome IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
        AND (:status IS NULL OR u.status = :status)
    """)
    Page<Advogado> buscarComFiltros(
            @Param("nome") String nome,
            @Param("status") UsuarioStatus status,
            Pageable pageable
    );

    @Query("""
        SELECT new com.advocacia.estacio.modules.advogados.AdvogadoDTO$OptionResponse(a.id, p.nome)
        FROM Advogado a
        JOIN a.pessoa p
        JOIN p.usuario u
        WHERE (:nome IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
        AND u.status IN :status
    """)
    List<AdvogadoDTO.OptionResponse> buscarAtivosPorNome(
            @Param("nome") String nome,
            @Param("status") List<UsuarioStatus> status,
            Pageable pageable
    );


//	@Query("""
//			SELECT new com.advocacia.estacio.modules.advogados.Advogado(
//				adv.id,
//				adv.nome
//			)
//			FROM Advogado adv WHERE adv.email = :email
//			""")
//	Optional<Advogado> buscarIdPorEmail(@Param("email") String email);
}
