package com.edutech.microservicio_principal.Curso;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
/*import org.mockito.Mockito;*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CursoController.class)
class CursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CursoService cursoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCrearCurso() throws Exception {
        Curso curso = new Curso(1L, "Curso de Vue.js", "Frontend avanzado", 18000);

        when(cursoService.guardarCurso(any())).thenReturn(curso);

        mockMvc.perform(post("/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(curso)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.nombre").value("Curso de Vue.js"));
    }

    @Test
    void testObtenerCursos() throws Exception {
        Curso curso = new Curso(1L, "Curso de Angular", "Frontend", 15000);
        when(cursoService.obtenerTodos()).thenReturn(List.of(curso));

        mockMvc.perform(get("/cursos"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].nombre").value("Curso de Angular"));
    }

    @Test
    void testObtenerCursoPorId_Existe() throws Exception {
        Curso curso = new Curso(1L, "Docker", "Contenedores", 13000);
        when(cursoService.obtenerPorId(1L)).thenReturn(Optional.of(curso));

        mockMvc.perform(get("/cursos/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.nombre").value("Docker"));
    }

    @Test
    void testObtenerCursoPorId_NoExiste() throws Exception {
        when(cursoService.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/cursos/99"))
               .andExpect(status().isNotFound());
    }

    @Test
    void testActualizarCurso_Existe() throws Exception {
        Curso original = new Curso(1L, "JavaScript", "Intro", 10000);
        Curso actualizado = new Curso(1L, "JavaScript Avanzado", "DOM y más", 14000);

        when(cursoService.obtenerPorId(1L)).thenReturn(Optional.of(original));
        when(cursoService.guardarCurso(any())).thenReturn(actualizado);

        mockMvc.perform(put("/cursos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.nombre").value("JavaScript Avanzado"));
    }

    @Test
    void testActualizarCurso_NoExiste() throws Exception {
        Curso actualizado = new Curso(1L, "Python", "Intro", 9000);
        when(cursoService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/cursos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
               .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarCurso_Existe() throws Exception {
        when(cursoService.eliminarCurso(1L)).thenReturn(true);

        mockMvc.perform(delete("/cursos/1"))
               .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarCurso_NoExiste() throws Exception {
        when(cursoService.eliminarCurso(99L)).thenReturn(false);

        mockMvc.perform(delete("/cursos/99"))
               .andExpect(status().isNotFound());
    }
}

