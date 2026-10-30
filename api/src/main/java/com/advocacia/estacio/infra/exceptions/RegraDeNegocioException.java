package com.advocacia.estacio.infra.exceptions;

/**
 * Exceção genérica para representar violações nas regras de negócio da aplicação.
 *
 * <p><b>Quando usar:</b></p>
 * Utilize esta exceção quando os dados enviados estão no formato correto, mas
 * a operação não é permitida devido às lógicas e fluxos definidos pelo negócio (cliente).
 *
 * <p><b>Exemplos práticos:</b></p>
 * <ul>
 *   <li>Um Estagiário tentando assumir uma Demanda sem a supervisão de um Professor.</li>
 *   <li>Tentativa de alterar o status de um Processo de "Arquivado" para "Em Andamento"
 *       se o sistema não permitir essa transição direta.</li>
 *   <li>Tentativa de desativar um usuário que possui processos ativos pendentes.</li>
 * </ul>
 *
 * <p><b>Comportamento na API:</b></p>
 * Esta exceção é interceptada pelo {@code ControllerAdviceApi} e retorna automaticamente
 * um HTTP Status <b>400 (Bad Request)</b>.
 *
 * <p><b>Atenção:</b></p>
 * Não utilize esta exceção para erros de validação de campos vazios/nulos
 * (para isso, use Bean Validation como @NotBlank, @NotNull).
 */
public class RegraDeNegocioException extends RuntimeException {
    public RegraDeNegocioException(String message) {
        super(message);
    }
}