package com.agenda;

import com.agenda.controller.ProfissionalDaSaudeController;
import com.agenda.model.Categoria;
import com.agenda.model.ProfissionalDaSaude;
import com.agenda.service.ProfissionalDaSaudeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfissionalDaSaudeController.class)
class ProfissionalDaSaudeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfissionalDaSaudeService profissionalDaSaudeService;

    @Test
    void deveCriarContatoComSucesso() throws Exception {
        Categoria categoria = new Categoria(1L, "Cardiologista");

        ProfissionalDaSaude profissional = new ProfissionalDaSaude(
                1L,
                "João Silva",
                "31999999999",
                "joao@email.com",
                "Rua A",
                categoria,
                LocalDateTime.now()
        );

        when(profissionalDaSaudeService.create(any(ProfissionalDaSaude.class)))
                .thenReturn(profissional);

        mockMvc.perform(post("/api/contatos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "João Silva",
                                  "telefone": "31999999999",
                                  "email": "joao@email.com",
                                  "endereco": "Rua A",
                                  "categoria": {
                                    "categoria": "Cardiologista"
                                  }
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.Categoria.categoria").value("Cardiologista"));
    }

    @Test
    void deveListarContatosVazio() throws Exception {
        when(profissionalDaSaudeService.listAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/contatos"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void deveRetornar404ParaContatoInexistente() throws Exception {
        when(profissionalDaSaudeService.get(999L))
                .thenThrow(new RuntimeException("Profissional da saúde não encontrado"));

        mockMvc.perform(get("/api/contatos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveDeletarContatoComSucesso() throws Exception {
        doNothing().when(profissionalDaSaudeService).delete(1L);

        mockMvc.perform(delete("/api/contatos/1"))
                .andExpect(status().isNoContent());
    }
}