package br.com.devstock.devstock.controller;

import br.com.devstock.devstock.exception.GlobalExceptionHandler;
import br.com.devstock.devstock.service.MovimentacaoService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovimentacaoController.class)
@Import(GlobalExceptionHandler.class)
class MovimentacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovimentacaoService movimentacaoService;

    @Test
    void deveRetornar400QuandoResponsavelNaoForInformado()
            throws Exception {

        mockMvc.perform(post("/api/notebooks/1/ocorrencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"responsavel\":\"\",\"observacao\":\"Notebook apresentou defeito\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").value("Dados inválidos"))
                .andExpect(jsonPath("$.mensagem").exists());

        verifyNoInteractions(movimentacaoService);
    }

    @Test
    void deveRetornar400QuandoDescricaoDaOcorrenciaNaoForInformada()
            throws Exception {

        mockMvc.perform(post("/api/notebooks/1/ocorrencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"responsavel\":\"Responsável de teste\",\"observacao\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").value("Dados inválidos"))
                .andExpect(jsonPath("$.mensagem").exists());

        verifyNoInteractions(movimentacaoService);
    }
}