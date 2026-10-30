//package com.advocacia.estacio.modules.demandas.comentarios;
//
//import com.advocacia.estacio.modules.demandas.Demanda;
//import com.advocacia.estacio.modules.pessoas.Pessoa;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import lombok.Builder;
//import lombok.Getter;
//import org.hibernate.annotations.CreationTimestamp;
//
//import java.io.Serializable;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@Table(name = "tbl_demanda_resposta")
//public class DemandaComentario implements Serializable {
//	private static final long serialVersionUID = 1L;
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//
//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "demanda_id", nullable = false)
//	private Demanda demanda;
//
//	@Column(nullable = false, columnDefinition = "TEXT")
//	private String comentario;
//
//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "autor_id", nullable = false)
//	private Pessoa autor;
//
//	@Column(name = "criado_em", updatable = false)
//	@CreationTimestamp
//	private LocalDateTime criadoEm;
//
//	protected DemandaComentario() {
//	}
//
//	// TODO: hashCode() e equals()
////	@Override
////	public boolean equals(Object o) {
////		if (this == o) return true;
////		if (!(o instanceof DemandaResposta outra)) return false;
////		return demanda != null && Objects.equals(demanda, outra.getDemanda()) &&
////				autorResposta != null && Objects.equals(autorResposta, outra.getAutorResposta()) &&
////				resposta != null && Objects.equals(resposta, outra.getResposta());
////	}
////
////	@Override
////	public int hashCode() {
////		return Objects.hash(demanda, autorResposta, resposta);
////	}
//
//	@Builder
//	public DemandaComentario(Pessoa autor, String comentario, Demanda demanda) {
//		this.autor = autor;
//		this.comentario = comentario;
//		this.demanda = demanda;
//	}
//}
