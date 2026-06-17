package com.agenda;

import com.agenda.repository.CategoriaRepository;
import com.agenda.repository.ProfissionalDaSaudeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class IntegracaoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProfissionalDaSaudeRepository profissionalDaSaudeRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @BeforeEach
    void limparBanco() {
        profissionalDaSaudeRepository.deleteAll();
        categoriaRepository.deleteAll();
    }

    @Test
    void deveExecutarFluxoCompletoProfissionalDaSaude() throws Exception {
        String profissionalJson = """
                {
                  "nome": "Maria Santos",
                  "telefone": "31988887777",
                  "email": "maria@email.com",
                  "endereco": "Rua A",
                  "categoria": {
                    "categoria": "Cardiologista"
                  }
                }
                """;

        String response = mockMvc.perform(post("/api/contatos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(profissionalJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.nome").value("Maria Santos"))
                .andExpect(jsonPath("$.Categoria.categoria").value("Cardiologista"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = response.replaceAll(".*\"id\":(\\d+).*", "$1");

        mockMvc.perform(get("/api/contatos/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Maria Santos"))
                .andExpect(jsonPath("$.Categoria.categoria").value("Cardiologista"));

        String profissionalAtualizadoJson = """
                {
                  "nome": "Maria Santos Silva",
                  "telefone": "31988887777",
                  "email": "maria.silva@email.com",
                  "endereco": "Rua B",
                  "categoria": {
                    "categoria": "Dermatologista"
                  }
                }
                """;

        mockMvc.perform(put("/api/contatos/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(profissionalAtualizadoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Maria Santos Silva"))
                .andExpect(jsonPath("$.Categoria.categoria").value("Dermatologista"));

        mockMvc.perform(delete("/api/contatos/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveCriarCategoriaEUsarNoProfissional() throws Exception {
        mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "categoria": "Psicólogo"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoria").value("Psicólogo"));

        mockMvc.perform(post("/api/contatos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Pedro Lima",
                                  "telefone": "31977776666",
                                  "email": "pedro@email.com",
                                  "endereco": "Rua C",
                                  "categoria": {
                                    "categoria": "Psicólogo"
                                  }
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Pedro Lima"))
                .andExpect(jsonPath("$.Categoria.categoria").value("Psicólogo"));
    }
}