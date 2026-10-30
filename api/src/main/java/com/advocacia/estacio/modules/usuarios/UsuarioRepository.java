package com.advocacia.estacio.modules.usuarios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByLogin(String login);

	@Query(
			value = """
        SELECT u FROM Usuario u
        JOIN FETCH u.pessoa p
        WHERE u.role IN :roles
    """,
			countQuery = """
        SELECT COUNT(u) FROM Usuario u
        WHERE u.role IN :roles
    """
	)
	Page<Usuario> buscarPorRoles(@Param("roles") List<UsuarioRole> roles, Pageable pageable);
}
