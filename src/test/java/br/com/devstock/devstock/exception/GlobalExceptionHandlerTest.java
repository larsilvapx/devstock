
package br.com.devstock.devstock.exception;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler =
            new GlobalExceptionHandler();

    @Test
    void deveRetornar404QuandoRecursoNaoForEncontrado() {

        RecursoNaoEncontradoException exception =
                new RecursoNaoEncontradoException("Notebook não encontrado.");

        ResponseEntity<Map<String, String>> resposta =
                handler.tratarRecursoNaoEncontrado(exception);

        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
        assertEquals("Recurso não encontrado",
                resposta.getBody().get("erro"));
        assertEquals("Notebook não encontrado.",
                resposta.getBody().get("mensagem"));
    }

    @Test
    void deveRetornar409QuandoOcorrerConflitoDeCadastro() {

        ConflitoCadastroException exception =
                new ConflitoCadastroException("Notebook já cadastrado.");

        ResponseEntity<Map<String, String>> resposta =
                handler.tratarConflitoCadastro(exception);

        assertEquals(HttpStatus.CONFLICT, resposta.getStatusCode());
        assertEquals("Conflito de dados",
                resposta.getBody().get("erro"));
        assertEquals("Notebook já cadastrado.",
                resposta.getBody().get("mensagem"));
    }

    @Test
    void deveRetornar409QuandoOcorrerViolacaoDeIntegridade() {

        DataIntegrityViolationException exception =
                new DataIntegrityViolationException("Erro interno do banco.");

        ResponseEntity<Map<String, String>> resposta =
                handler.tratarViolacaoIntegridade(exception);

        assertEquals(HttpStatus.CONFLICT, resposta.getStatusCode());
        assertEquals("Conflito de dados",
                resposta.getBody().get("erro"));
        assertEquals(
                "A operação viola uma restrição de integridade dos dados.",
                resposta.getBody().get("mensagem")
        );
    }

    @Test
    void deveRetornar400QuandoArgumentoForInvalido() {

        IllegalArgumentException exception =
                new IllegalArgumentException("Dados inválidos.");

        ResponseEntity<Map<String, String>> resposta =
                handler.tratarArgumentoInvalido(exception);

        assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
        assertEquals("Dados inválidos",
                resposta.getBody().get("erro"));
        assertEquals("Dados inválidos.",
                resposta.getBody().get("mensagem"));
    }

    @Test
    void deveRetornar400QuandoArgumentoNaoPossuirMensagem() {

        IllegalArgumentException exception =
                new IllegalArgumentException();

        ResponseEntity<Map<String, String>> resposta =
                handler.tratarArgumentoInvalido(exception);

        assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
        assertEquals(
                "Os dados informados são inválidos.",
                resposta.getBody().get("mensagem")
        );
    }

    @Test
    void deveRetornar500QuandoOcorrerErroInesperado() {

        Exception exception =
                new Exception("Erro interno de teste.");

        ResponseEntity<Map<String, String>> resposta =
                handler.tratarErroInterno(exception);

        assertEquals(
                HttpStatus.INTERNAL_SERVER_ERROR,
                resposta.getStatusCode()
        );

        assertEquals("Erro interno no servidor",
                resposta.getBody().get("erro"));

        assertEquals(
                "Ocorreu um erro inesperado. Tente novamente mais tarde.",
                resposta.getBody().get("mensagem")
        );
    }
}
