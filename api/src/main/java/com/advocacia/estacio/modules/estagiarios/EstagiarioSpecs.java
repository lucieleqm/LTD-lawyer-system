package com.advocacia.estacio.modules.estagiarios;

import com.advocacia.estacio.modules.pessoas.Pessoa;
import com.advocacia.estacio.modules.usuarios.Usuario;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EstagiarioSpecs {
    public static Specification<Estagiario> usandoFiltro(EstagiarioDTO.SearchFilter filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            boolean isCountQuery = query.getResultType() == Long.class || query.getResultType() == long.class;

            Join<Estagiario, Pessoa> pessoaJoin;

            if(isCountQuery) {
                pessoaJoin = root.join("pessoa", JoinType.INNER);
            } else {
                Fetch<Estagiario, Pessoa> pessoaFetch = root.fetch("pessoa", JoinType.INNER);
                pessoaJoin = (Join<Estagiario, Pessoa>) pessoaFetch;
                pessoaFetch.fetch("usuario", JoinType.LEFT);
            }

            if (StringUtils.hasText(filtro.termo())) {
                String termoBusca = "%" + filtro.termo().toLowerCase() + "%";
                Predicate buscaPorNome = cb.like(cb.lower(pessoaJoin.get("nome")), termoBusca);
                Predicate buscaPorMatricula = cb.like(cb.lower(root.get("matricula")), termoBusca);
                predicates.add(cb.or(buscaPorNome, buscaPorMatricula));
            }

            if (filtro.periodoEstagio() != null) {
                predicates.add(cb.equal(root.get("periodo"), filtro.periodoEstagio()));
            }
            if (filtro.usuarioStatus() != null) {
                Join<Pessoa, Usuario> usuarioJoin = pessoaJoin.join("usuario", JoinType.INNER);
                predicates.add(cb.equal(usuarioJoin.get("status"), filtro.usuarioStatus()));
            }

            query.distinct(true);

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
