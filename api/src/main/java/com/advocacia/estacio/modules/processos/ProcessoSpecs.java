package com.advocacia.estacio.modules.processos;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProcessoSpecs {
    private static final List<String> PAPEIS = List.of("advogado", "estagiario", "assistido");

    public static Specification<Processo> envolvePessoa(Long pessoaId) {
        return (root, query, cb) -> {
            Predicate[] predicates = PAPEIS.stream()
                    .map(papel -> cb.equal(root.get(papel).get("id"), pessoaId))
                    .toArray(Predicate[]::new);
            return cb.or(predicates);
        };
    }

    @SuppressWarnings("unchecked")
    public static Specification<Processo> comFiltros(ProcessoDTO.SearchFilter filtro) {
        return (root, query, cb) -> {
            boolean isCount = Long.class == query.getResultType() || long.class == query.getResultType();

            Map<String, Path<Object>> pessoas = new HashMap<>();
            if (!isCount) {
                for(String papel : PAPEIS) {
                    Fetch<Object, Object> f = root.fetch(papel, JoinType.LEFT);
                    Join<Object, Object> pessoa = (Join<Object, Object>) f.fetch("pessoa", JoinType.LEFT);
                    pessoas.put(papel, pessoa);
                }
            }  else if(hasTermo(filtro)) {
                for(String papel : PAPEIS) {
                    pessoas.put(papel, root.join(papel, JoinType.LEFT).join("pessoa", JoinType.LEFT));
                }
            }

            List<Predicate> predicates = new ArrayList<>();

            if (filtro.status() != null) {
                predicates.add(cb.equal(root.get("status"), filtro.status()));
            }
            if (filtro.areaDireito() != null) {
                predicates.add(cb.equal(root.get("areaDireito"), filtro.areaDireito()));
            }
            if (filtro.tribunal() != null) {
                predicates.add(cb.equal(root.get("tribunal"), filtro.tribunal()));
            }

            if (hasTermo(filtro)) {
                String termo = "%" + filtro.termo().trim().toLowerCase()  + "%";
                List<Predicate> matches = PAPEIS.stream()
                        .map(papel -> cb.like(pessoas.get(papel).get("nome"), termo))
                        .collect(Collectors.toList());

                matches.add(cb.like(cb.lower(root.get("numeroProcesso")), termo));
                matches.add(cb.like(cb.lower(root.get("numeroProcessoPje")), termo));

                predicates.add(cb.or(matches.toArray(new Predicate[0])));
            }

            query.distinct(true);
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
    private static boolean hasTermo(ProcessoDTO.SearchFilter filtro) {
        return filtro.termo() != null && !filtro.termo().isBlank();
    }
}
