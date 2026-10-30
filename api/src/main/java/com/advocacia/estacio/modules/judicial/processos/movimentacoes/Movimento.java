//package com.advocacia.estacio.modules.processos.movimentacoes;
//
//import java.time.LocalDateTime;
//
//import com.advocacia.estacio.modules.advogados.Advogado;
//import com.advocacia.estacio.modules.processos.Processo;
//import jakarta.persistence.*;
//import lombok.Builder;
//import lombok.Getter;
//import org.hibernate.annotations.CreationTimestamp;
//
//@Entity
//@Getter
//@Table(name = "tbl_movimento")
//public class Movimento {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//
//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "processo_id", nullable = false)
//	private Processo processo;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "advogado_id")
//	private Advogado advogado;
//
////	@Column(nullable = false, columnDefinition = "TEXT")
////	private String descricao;
//
//	@Column(name = "criacao", updatable = false)
//	@CreationTimestamp
//	private LocalDateTime criacao;
//
//	protected Movimento() {
//	}
//
//	// TODO: hashCode() e equals
//
//	@Builder
//	public Movimento(Processo processo, Advogado advogado) {
//		this.processo = processo;
//		this.advogado = advogado;
//	}
//}
