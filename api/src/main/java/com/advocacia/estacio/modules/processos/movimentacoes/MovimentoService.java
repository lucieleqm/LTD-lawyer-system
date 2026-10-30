//package com.advocacia.estacio.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.stereotype.Service;
//
//import com.advocacia.estacio.domain.dto.MovimentoDto;
//import com.advocacia.estacio.modules.advogados.Advogado;
//import com.advocacia.estacio.modules.processos.movimentacoes.Movimento;
//import com.advocacia.estacio.modules.processos.Processo;
//import com.advocacia.estacio.modules.processos.movimentacoes.MovimentoRepository;
//
//@Service
//public class MovimentoService {
//
//	@Autowired
//	private MovimentoRepository movimentoRepository;
//
//	@Autowired
//	private AdvogadoService advogadoService;
//
//	@Autowired
//	private ProcessoService processoService;
//
//	public Movimento salvar(MovimentoDto movimentoDto) {
//		Processo processo = processoService.buscarPorId(movimentoDto.getProcessoId());
//		Advogado advogado = advogadoService.buscarPorId(movimentoDto.getAdvogadoId());
//		Movimento movimento = new Movimento(movimentoDto);
//		movimento.setAdvogado(advogado);
//		movimento.setProcesso(processo);
//		return movimentoRepository.save(movimento);
//	}
//
//
//	public Page<Movimento> buscarMovimentosPorProcesso(
//			String numeroDoProcesso,
//			Pageable pageable) {
//		Processo processo = processoService.buscarPorNumeroDoProcesso(numeroDoProcesso);
//		return movimentoRepository.findAllByProcesso(processo, pageable);
//	}
//}
