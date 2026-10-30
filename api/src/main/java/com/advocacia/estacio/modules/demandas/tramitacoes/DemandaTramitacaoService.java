package com.advocacia.estacio.modules.demandas.tramitacoes;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DemandaTramitacaoService {
    private final DemandaTramitacaoRepository tramitacaoRepository;

    @Transactional(readOnly = true)
    public Page<DemandaTramitacaoDTO.Response> listarPorDemanda(Long demandaId, Pageable pageable) {
        return tramitacaoRepository.findByDemandaId(demandaId, pageable).map(DemandaTramitacaoDTO.Response::new);
    }
}
