package br.com.devstock.devstock.controller;

import br.com.devstock.devstock.exception.GlobalExceptionHandler;
import br.com.devstock.devstock.service.OnboardService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OnboardController.class)
@Import(GlobalExceptionHandler.class)
class OnboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OnboardService onboardService;

    @Test
    void deveRetornar400QuandoIdsDoOnboardingNaoForemInformados()
            throws Exception {

        mockMvc.perform(post("/api/onboardings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").value("Dados inválidos"))
                .andExpect(jsonPath("$.mensagem").exists());

        verifyNoInteractions(onboardService);
    }

    @Test
    void deveRetornar400QuandoResponsavelDaEntregaNaoForInformado()
            throws Exception {

        mockMvc.perform(put("/api/onboardings/1/confirmar-entrega")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"responsavel\":\"\",\"observacao\":\"Entrega do equipamento\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").value("Dados inválidos"))
                .andExpect(jsonPath("$.mensagem").exists());

        verifyNoInteractions(onboardService);
    }

    @Test
    void deveRetornar400QuandoListaDeItensDaDevolucaoEstiverVazia()
            throws Exception {

        mockMvc.perform(put("/api/onboardings/1/confirmar-devolucao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"responsavel\":\"Responsável de teste\",\"observacao\":\"Devolução dos equipamentos\",\"itens\":[]}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").value("Dados inválidos"))
                .andExpect(jsonPath("$.mensagem").exists());

        verifyNoInteractions(onboardService);
    }
}