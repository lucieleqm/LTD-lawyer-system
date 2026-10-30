//package com.advocacia.estacio.web.controllers;
//
//import com.advocacia.estacio.services.MovimentoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.advocacia.estacio.domain.dto.MovimentoDto;
//import com.advocacia.estacio.domain.dto.MovimentoResponseDto;
//import com.advocacia.estacio.domain.dto.PageResponseDto;
//import com.advocacia.estacio.modules.processos.movimentacoes.Movimento;
//
//@RequestMapping("/movimentos")
//@RestController
//public class MovimentoController {
//
//	@Autowired
//	MovimentoService movimentoService;
//
//	@PostMapping("/")
//	public ResponseEntity<MovimentoDto> salvar(@RequestBody MovimentoDto movimentoDto) {
//		Movimento movimento = movimentoService.salvar(movimentoDto);
//		return ResponseEntity.status(201).body(new MovimentoDto(movimento));
//	}
//
//	@GetMapping("/buscar/{numeroDoProcesso}")
//	public ResponseEntity<PageResponseDto<MovimentoResponseDto>> buscarMovimentosPorProcesso(
//			@PathVariable String numeroDoProcesso,
//			@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
//			) {
//		Page<Movimento> pages = movimentoService.buscarMovimentosPorProcesso(numeroDoProcesso, pageable);
//		Page<MovimentoResponseDto> pagesDto = pages.map(MovimentoResponseDto::new);
//		return ResponseEntity.ok(new PageResponseDto<>(pagesDto));
//	}
//}
