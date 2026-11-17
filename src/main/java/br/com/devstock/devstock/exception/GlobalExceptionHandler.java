
package br.com.devstock.devstock.exception;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> tratarRecursoNaoEncontrado(
            RecursoNaoEncontradoException ex) {

        return criarResposta(
                HttpStatus.NOT_FOUND,
                "Recurso não encontrado",
                obterMensagem(ex, "O recurso solicitado não foi encontrado.")
        );
    }

    @ExceptionHandler(ConflitoCadastroException.class)
    public ResponseEntity<Map<String, String>> tratarConflitoCadastro(
            ConflitoCadastroException ex) {

        return criarResposta(
                HttpStatus.CONFLICT,
                "Conflito de dados",
                obterMensagem(ex, "O cadastro apresenta um conflito.")
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> tratarViolacaoIntegridade(
            DataIntegrityViolationException ex) {

        logger.warn("Violação de integridade dos dados.", ex);

        return criarResposta(
                HttpStatus.CONFLICT,
                "Conflito de dados",
                "A operação viola uma restrição de integridade dos dados."
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> tratarCorpoInvalido(
            HttpMessageNotReadableException ex) {

        return criarResposta(
                HttpStatus.BAD_REQUEST,
                "Requisição inválida",
                "O corpo da requisição está malformado ou contém valores incompatíveis com os tipos esperados."
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> tratarParametroInvalido(
            MethodArgumentTypeMismatchException ex) {

        return criarResposta(
                HttpStatus.BAD_REQUEST,
                "Parâmetro inválido",
                "Um parâmetro da requisição possui formato incompatível com o esperado."
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarValidacaoCampos(
            MethodArgumentNotValidException ex) {

        String mensagem = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .orElse("Um ou mais campos possuem valores inválidos.");

        return criarResposta(
                HttpStatus.BAD_REQUEST,
                "Dados inválidos",
                mensagem
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> tratarViolacaoValidacao(
            ConstraintViolationException ex) {

        String mensagem = ex.getConstraintViolations()
                .stream()
                .findFirst()
                .map(violacao -> violacao.getPropertyPath()
                        + ": " + violacao.getMessage())
                .orElse("Um ou mais parâmetros possuem valores inválidos.");

        return criarResposta(
                HttpStatus.BAD_REQUEST,
                "Dados inválidos",
                mensagem
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> tratarArgumentoInvalido(
            IllegalArgumentException ex) {

        return criarResposta(
                HttpStatus.BAD_REQUEST,
                "Dados inválidos",
                obterMensagem(ex, "Os dados informados são inválidos.")
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> tratarErroInterno(
            Exception ex) {

        logger.error("Ocorreu um erro inesperado na aplicação.", ex);

        return criarResposta(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno no servidor",
                "Ocorreu um erro inesperado. Tente novamente mais tarde."
        );
    }

    private ResponseEntity<Map<String, String>> criarResposta(
            HttpStatus status,
            String erro,
            String mensagem) {

        Map<String, String> resposta = Map.of(
                "erro", erro,
                "mensagem", mensagem
        );

        return ResponseEntity.status(status).body(resposta);
    }

    private String obterMensagem(Exception ex, String mensagemPadrao) {

        if (ex.getMessage() == null || ex.getMessage().isBlank()) {
            return mensagemPadrao;
        }

        return ex.getMessage();
    }
}
