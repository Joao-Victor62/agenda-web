package com.agenda;

import com.agenda.controller.CategoriaController;
import com.agenda.model.Categoria;
import com.agenda.service.CategoriaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoriaController.class)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService categoriaService;

    @Test
    void deveCriarCategoriaComSucesso() throws Exception {
        Categoria categoria = new Categoria(1L, "Cardiologista");

        when(categoriaService.create(any(Categoria.class))).thenReturn(categoria);

        mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "categoria": "Cardiologista"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.categoria").value("Cardiologista"));
    }

    @Test
    void deveListarCategorias() throws Exception {
        Categoria categoria1 = new Categoria(1L, "Cardiologista");
        Categoria categoria2 = new Categoria(2L, "Dermatologista");

        when(categoriaService.listAll()).thenReturn(List.of(categoria1, categoria2));

        mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].categoria").value("Cardiologista"))
                .andExpect(jsonPath("$[1].categoria").value("Dermatologista"));
    }

    @Test
    void deveDeletarCategoriaComSucesso() throws Exception {
        mockMvc.perform(delete("/api/categorias/1"))
                .andExpect(status().isNoContent());
    }
}