//package com.advocacia.estacio.web.controllers;
//
//import com.advocacia.estacio.domain.dto.DemandaRespondeDto;
//import com.advocacia.estacio.domain.dto.PageResponseDto;
//import com.advocacia.estacio.modules.demandas.comentarios.DemandaComentario;
//import com.advocacia.estacio.services.DemandaComentarioService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RequestMapping("/demandas/responde")
//@RestController
//public class DemandaRespondeController {
//
//	@Autowired
//	DemandaComentarioService demandaComentarioService;
//
//	@PostMapping("/")
//	public ResponseEntity<DemandaRespondeDto> salvar(@RequestBody DemandaRespondeDto dto) {
//		DemandaComentario demandaComentario = demandaComentarioService.salvar(dto);
//		return ResponseEntity.status(201).body(new DemandaRespondeDto(demandaComentario));
//	}
//
//	@GetMapping("/demanda/{demandaId}")
//	public ResponseEntity<PageResponseDto<DemandaRespondeDto>> buscarDemandasRespostasPorEstagiarioId(
//			@PathVariable Long demandaId,
//			@RequestParam(defaultValue = "0") int page,
//			@RequestParam(defaultValue = "20") int size
//	) {
//		Page<DemandaRespondeDto> pages = demandaComentarioService.buscarDemandasRespostasPorDemandaId(demandaId, page, size);
//		return ResponseEntity.ok(new PageResponseDto<>(pages));
//	}
//}
