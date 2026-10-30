package com.advocacia.estacio.infra.exceptions.handler;

import com.advocacia.estacio.infra.exceptions.ConflitoDeDadosException;
import com.advocacia.estacio.infra.exceptions.RegraDeNegocioException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ControllerAdviceApi {

    // (400) - Erros do Bean Validation (Fields)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handle400Error(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Erro de validação nos campos."
        );
        problemDetail.setTitle("Dados Inválidos");
        problemDetail.setType(URI.create("/docs/erros.html#dados-invalidos"));
        record FieldErrorDto(String field, String message) {}

        List<FieldErrorDto> erros = ex.getFieldErrors().stream()
                .map(error -> new FieldErrorDto(error.getField(), error.getDefaultMessage()))
                .toList();

        problemDetail.setProperty("fields", erros);

        return problemDetail;
    }

    // (400) - Erros de Regra de Negócio Gerais
    @ExceptionHandler(RegraDeNegocioException.class)
    public ProblemDetail handleBusinessRule(RegraDeNegocioException ex) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setType(URI.create("/docs/erros.html#violacao-regra-negocio"));
        problemDetail.setTitle("Violação de Regra de Negócio");
        return problemDetail;
    }

    // (400) - Erro de integridade de dados (Ex: Chave estrangeira não existe, Unique constraint)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        var problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Não foi possível concluir a operação. Isso geralmente ocorre quando você tenta cadastrar uma informação que já existe no sistema ou quando um dado relacionado está incorreto."
        );
        problemDetail.setType(URI.create("/docs/erros.html#violacao-de-integridade"));
        problemDetail.setTitle("Violação de Integridade");
        return problemDetail;
    }

    // (404) - Entidade n encontrada
    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFound(EntityNotFoundException ex) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("/docs/erros.html#recurso-nao-encontrado"));
        problemDetail.setTitle("Recurso Não Encontrado ");
        return problemDetail;
    }

    // (409) - Conflito de Dados / Duplicidade
    @ExceptionHandler(ConflitoDeDadosException.class)
    public ProblemDetail handleConflict(ConflitoDeDadosException ex) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setType(URI.create("/docs/erros.html#conflito-de-dados"));
        problemDetail.setTitle("Conflito de Dados");
        return problemDetail;
    }


    // =========================================================================
    // EXCEÇÕES DE SEGURANÇA (SPRING SECURITY)
    // =========================================================================

    // (401) - Login e Senha incorretos
    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentials(BadCredentialsException ex) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Login ou senha incorretos.");
        problemDetail.setType(URI.create("/docs/erros.html#credenciais-invalidas"));
        problemDetail.setTitle("Credenciais Inválidas");

        return problemDetail;
    }

    // (403) - Usuário sem permissão (role errada)
    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDenied(AccessDeniedException ex) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Você não tem permissão para acessar este recurso.");
        problemDetail.setType(URI.create("/docs/erros.html#acesso-negado"));
        problemDetail.setTitle("Acesso Negado");

        return problemDetail;
    }

    // (403) - Conta Bloqueada
    @ExceptionHandler(LockedException.class)
    public ProblemDetail handleLockedException(LockedException ex) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Acesso temporariamente bloqueado.");
        problemDetail.setType(URI.create("/docs/erros.html#conta-bloqueada"));
        problemDetail.setTitle("Conta Bloqueada");

        return problemDetail;
    }

    // (403) - Conta Inativa
    @ExceptionHandler(DisabledException.class)
    public ProblemDetail handleDisabledException(DisabledException ex) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Esta conta foi inativada. Entre em contato com a administração.");
        problemDetail.setType(URI.create("/docs/erros.html#conta-inativa"));
        problemDetail.setTitle("Conta Inativa");

        return problemDetail;
    }

    // =========================================================================
    // FALLBACK GERAL
    // =========================================================================

    // (500) - Erros Inesperados do Servidor
    @ExceptionHandler(Exception.class)
    public ProblemDetail handle500Error(Exception ex) {
        log.error("Erro interno inesperado: ", ex);
        var problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro interno inesperado no servidor. Tente novamente mais tarde."
        );
        problemDetail.setType(URI.create("/docs/erros.html#erro-interno-servidor"));
        problemDetail.setTitle("Erro Interno do Servidor");

        return problemDetail;
    }
}