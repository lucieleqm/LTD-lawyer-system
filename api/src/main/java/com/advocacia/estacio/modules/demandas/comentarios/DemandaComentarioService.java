//package com.advocacia.estacio.services;
//
//
//import com.advocacia.estacio.domain.dto.DemandaRespondeDto;
//import com.advocacia.estacio.modules.demandas.entities.Demanda;
//import com.advocacia.estacio.modules.demandas.entities.DemandaComentario;
//import com.advocacia.estacio.modules.estagiarios.Estagiario;
//import com.advocacia.estacio.repositories.DemandaRespondeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//@Service
//public class DemandaComentarioService {
//
//	@Autowired
//	private DemandaRespondeRepository demandaRespondeRepository;
//
//	@Autowired
//	private EstagiarioService estagiarioService;
//
//	@Autowired
//	private DemandaService demandaService;
//
//	@Override
//	public DemandaComentario salvar(DemandaRespondeDto dto) {
//		Demanda demanda = demandaService.buscarPorId(dto.getDemandaId());
//		Estagiario estagiario = estagiarioService.buscarPorId(dto.getEstagiarioId());
//		DemandaComentario demandaComentario = new DemandaComentario(dto);
//		demandaComentario.setDemanda(demanda);
//		demandaComentario.setEstagiario(estagiario);
//		return demandaRespondeRepository.save(demandaComentario);
//	}
//
//	@Override
//	public Page<DemandaRespondeDto> buscarDemandasRespostasPorDemandaId(Long demandaId, int page, int size) {
//		PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
//		return demandaRespondeRepository.buscarDemandasRespostasPorDemandaId(demandaId, pageable);
//	}
//}
