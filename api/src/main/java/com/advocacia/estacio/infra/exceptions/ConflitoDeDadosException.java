package com.advocacia.estacio.infra.exceptions;

/**
 * Exceção genérica para representar conflitos de integridade ou duplicidade de dados no sistema.
 *
 * <p><b>Quando usar:</b></p>
 * Utilize esta exceção sempre que uma operação falhar porque um dado único já existe no banco
 * ou quando a ação violar uma restrição de unicidade.
 *
 * <p><b>Exemplos práticos:</b></p>
 * <ul>
 *   <li>Tentativa de cadastrar um Usuário com um e-mail/login que já existe.</li>
 *   <li>Tentativa de cadastrar um Processo com um número de processo já registrado.</li>
 *   <li>Tentativa de associar um Assistido a uma Demanda que ele já faz parte.</li>
 * </ul>
 *
 * <p><b>Comportamento na API:</b></p>
 * Esta exceção é interceptada pelo {@code ControllerAdviceApi} e retorna automaticamente
 * um HTTP Status <b>409 (Conflict)</b>.
 *
 * <p><b>Nota Arquitetural:</b></p>
 * O uso desta classe evita a criação excessiva de exceções específicas como
 * {@code EmailJaCadastradoException} ou {@code NumeroProcessoExistenteException}.
 */
public class ConflitoDeDadosException extends RuntimeException {
    public ConflitoDeDadosException(String message) {
        super(message);
    }
}
