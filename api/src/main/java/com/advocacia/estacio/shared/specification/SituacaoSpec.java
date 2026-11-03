package com.advocacia.estacio.shared.specification;

import com.advocacia.estacio.shared.entity.Situacao;
import org.springframework.data.jpa.domain.Specification;

public final class SituacaoSpec {
    private SituacaoSpec() {}

    public static <T> Specification<T> de(Situacao situacao) {
        return (root, query, cb) -> (
                situacao == null || situacao.valor() == null)
                ? cb.conjunction()
                : cb.equal(root.get("ativo"), situacao.valor());
    }
}