
package br.com.devstock.devstock.controller;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import br.com.devstock.devstock.entity.Notebook;
import br.com.devstock.devstock.exception.ConflitoCadastroException;
import br.com.devstock.devstock.exception.GlobalExceptionHandler;
import br.com.devstock.devstock.exception.RecursoNaoEncontradoException;
import br.com.devstock.devstock.service.NotebookService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotebookController.class)
@Import(GlobalExceptionHandler.class)
class NotebookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotebookService notebookService;

    @Test
    void deveRetornar404QuandoNotebookNaoForEncontrado() throws Exception {

        when(notebookService.buscarPorId(9999L))
                .thenThrow(
                        new RecursoNaoEncontradoException(
                                "Notebook não encontrado com ID: 9999"
                        )
                );

        mockMvc.perform(get("/api/notebooks/9999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON
                ))
                .andExpect(jsonPath("$.erro")
                        .value("Recurso não encontrado"))
                .andExpect(jsonPath("$.mensagem")
                        .value("Notebook não encontrado com ID: 9999"));
    }

    @Test
    void deveRetornar409QuandoNumeroDeSerieJaEstiverCadastrado()
            throws Exception {

        when(notebookService.cadastrar(org.mockito.ArgumentMatchers.any()))
                .thenThrow(
                        new ConflitoCadastroException(
                                "Já existe um notebook com este número de série."
                        )
                );

        mockMvc.perform(post("/api/notebooks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON
                ))
                .andExpect(jsonPath("$.erro")
                        .value("Conflito de dados"))
                .andExpect(jsonPath("$.mensagem")
                        .value("Já existe um notebook com este número de série."));
    }


    @Test
    void deveRetornar201QuandoNotebookForCadastrado() throws Exception {

        Notebook notebookSalvo = new Notebook();
        notebookSalvo.setId(101L);
        notebookSalvo.setSerialNumber("SN-TESTE-101");
        notebookSalvo.setMarca("Dell");
        notebookSalvo.setModelo("Latitude 5440");
        notebookSalvo.setStatus("DISPONIVEL");

        when(notebookService.cadastrar(
                org.mockito.ArgumentMatchers.any(Notebook.class)
        )).thenReturn(notebookSalvo);

        mockMvc.perform(post("/api/notebooks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "serialNumber": "SN-TESTE-101",
                                    "marca": "Dell",
                                    "modelo": "Latitude 5440",
                                    "status": "DISPONIVEL"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string(
                        "Location",
                        org.hamcrest.Matchers.endsWith("/api/notebooks/101")
                ))
                .andExpect(content().contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON
                ))
                .andExpect(jsonPath("$.id").value(101))
                .andExpect(jsonPath("$.serialNumber").value("SN-TESTE-101"))
                .andExpect(jsonPath("$.marca").value("Dell"))
                .andExpect(jsonPath("$.modelo").value("Latitude 5440"))
                .andExpect(jsonPath("$.status").value("DISPONIVEL"));
    }


    @Test
    void deveRetornar200QuandoListarNotebooks() throws Exception {

        Notebook notebook = new Notebook();
        notebook.setId(101L);
        notebook.setSerialNumber("SN-TESTE-101");
        notebook.setMarca("Dell");
        notebook.setModelo("Latitude 5440");
        notebook.setStatus("DISPONIVEL");

        when(notebookService.listarTodos())
                .thenReturn(List.of(notebook));

        mockMvc.perform(get("/api/notebooks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(101))
                .andExpect(jsonPath("$[0].serialNumber")
                        .value("SN-TESTE-101"))
                .andExpect(jsonPath("$[0].marca").value("Dell"));
    }

    @Test
    void deveRetornar200QuandoAtualizarNotebook() throws Exception {

        Notebook notebookAtualizado = new Notebook();
        notebookAtualizado.setId(101L);
        notebookAtualizado.setSerialNumber("SN-TESTE-101");
        notebookAtualizado.setMarca("Dell");
        notebookAtualizado.setModelo("Latitude 5450");
        notebookAtualizado.setStatus("DISPONIVEL");

        when(notebookService.atualizar(
                org.mockito.ArgumentMatchers.eq(101L),
                org.mockito.ArgumentMatchers.any(Notebook.class)
        )).thenReturn(notebookAtualizado);

        mockMvc.perform(put("/api/notebooks/101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "serialNumber": "SN-TESTE-101",
                                    "marca": "Dell",
                                    "modelo": "Latitude 5450",
                                    "status": "DISPONIVEL"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(101))
                .andExpect(jsonPath("$.modelo").value("Latitude 5450"));
    }

    @Test
    void deveRetornar204QuandoExcluirNotebook() throws Exception {

        mockMvc.perform(delete("/api/notebooks/101"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }


}
