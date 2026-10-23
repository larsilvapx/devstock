
package br.com.devstock.devstock.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ConflitoCadastroException.class)
    public ResponseEntity<Map<String, String>> tratarConflitoCadastro(
            ConflitoCadastroException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "erro", "Conflito de cadastro",
                        "mensagem", exception.getMessage()
                ));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> tratarViolacaoIntegridade(
            DataIntegrityViolationException exception) {

        logger.error("Erro de integridade ao persistir dados no banco.", exception);

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "erro", "Conflito de dados",
                        "mensagem",
                        "Não foi possível concluir o cadastro. "
                                + "Verifique se os dados já existem."
                ));
    }
}
