package com.advocacia.estacio.modules.atendimento.demandas;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemandaSpecs {
    private static final ZoneId FUSO = ZoneId.of("America/Fortaleza");
    private static final List<String> PAPEIS = List.of("advogado", "professor", "estagiario");

    public static Specification<Demanda> envolvePessoa(Long pessoaId) {
        return (root, query, cb) -> {
            Predicate[] predicates = PAPEIS.stream()
                    .map(papel -> cb.equal(root.get(papel).get("id"), pessoaId))
                    .toArray(Predicate[]::new);
            return cb.or(predicates);
        };
    }

    @SuppressWarnings("unchecked")
    public static Specification<Demanda> comFiltros(DemandaDTO.SearchFilter filtro) {
        return (root, query, cb) -> {
            boolean isCount = Long.class == query.getResultType() || long.class == query.getResultType();

            Map<String, Path<Object>> pessoas = new HashMap<>();
            if(!isCount) {
                for(String papel : PAPEIS) {
                    Fetch<Object, Object> f = root.fetch(papel, JoinType.LEFT);
                    Join<Object, Object> pessoa = (Join<Object, Object>) f.fetch("pessoa", JoinType.LEFT);
                    pessoas.put(papel, pessoa);
                }
            } else if(hasTermo(filtro)) {
                for(String papel : PAPEIS) {
                    pessoas.put(papel, root.join(papel, JoinType.LEFT).join("pessoa", JoinType.LEFT));
                }
            }

            List<Predicate> predicates = new ArrayList<>();

            if (filtro.etapaAtual() != null) {
                predicates.add(cb.equal(root.get("etapaAtual"), filtro.etapaAtual()));
            }
            if (filtro.tempestividade() != null) {
                predicates.add(tempestividade(filtro.tempestividade(), root, cb));
            }
            if (hasTermo(filtro)) {
                String termo = "%" + filtro.termo().trim().toLowerCase()  + "%";
                Predicate[] matches = PAPEIS.stream()
                        .map(papel -> cb.like(pessoas.get(papel).get("nome"), termo))
                        .toArray(Predicate[]::new);
                predicates.add(cb.or(matches));
            }

            query.distinct(true);
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static boolean hasTermo(DemandaDTO.SearchFilter filtro) {
        return filtro.termo() != null && !filtro.termo().isBlank();
    }

    private static Predicate tempestividade(Tempestividade valor, Root<Demanda> root, CriteriaBuilder cb) {
        LocalDate hoje = LocalDate.now(FUSO);
        Path<LocalDate> prazoFinal = root.get("prazoFinal");

        return valor == Tempestividade.FORA_DO_PRAZO
                ? cb.lessThan(prazoFinal, hoje)
                : cb.greaterThanOrEqualTo(prazoFinal, hoje);
    }
}
