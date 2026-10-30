//package com.advocacia.estacio.modules.demandas.comentarios;
//
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//
//import java.time.LocalDateTime;
//
//public interface DemandaComentarioDTO {
//    record Request(
//            @NotNull Long demandaId,
//            @NotNull Long autorRespostaId, // Aponta para a tabela Pessoa
//            @NotBlank String resposta
//    ) {}
//
//    record Response(
//            Long id,
//            String resposta,
//            String nomeAutor, // Extraído da entidade Pessoa para não carregar o objeto inteiro
//            LocalDateTime createdAt
//    ) {}
//}
