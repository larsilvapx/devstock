package br.com.devstock.devstock.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

        Map<String, String> resposta = Map.of(
                "erro", "Recurso não encontrado",
                "mensagem", ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }

    @ExceptionHandler(ConflitoCadastroException.class)
    public ResponseEntity<Map<String, String>> tratarConflitoCadastro(
            ConflitoCadastroException ex) {

        Map<String, String> resposta = Map.of(
                "erro", "Conflito de dados",
                "mensagem", ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(resposta);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> tratarViolacaoIntegridade(
            DataIntegrityViolationException ex) {

        Map<String, String> resposta = Map.of(
                "erro", "Conflito de dados",
                "mensagem",
                "A operação viola uma restrição de integridade dos dados."
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(resposta);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> tratarCorpoInvalido(
            HttpMessageNotReadableException ex) {

        Map<String, String> resposta = Map.of(
                "erro", "Requisição inválida",
                "mensagem",
                "O corpo da requisição está malformado ou contém valores incompatíveis com os tipos esperados."
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> tratarParametroInvalido(
            MethodArgumentTypeMismatchException ex) {

        Map<String, String> resposta = Map.of(
                "erro", "Parâmetro inválido",
                "mensagem",
                "Um parâmetro da requisição possui formato incompatível com o esperado."
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }

    public ResponseEntity<Map<String, String>> tratarErroInterno(Exception ex){

        logger.error("Ocorreu um erro inexperado na aplicação", ex);

        Map<String, String> resposta = Map.of(
                "erro", "erro interno no servidor.",
                "mensagem", "Ocorreu um erro inesperado Tente novamente mais tarde."
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(resposta);
    }
}