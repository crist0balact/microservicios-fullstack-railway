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

@WebMvcTest(CursoController.class) // Solo se carga el contexto del CursoController para pruebas web
class CursoControllerTest {

    @Autowired
    private MockMvc mockMvc; // Permite simular peticiones HTTP a los endpoints REST

    @MockBean
    private CursoService cursoService; // Se inyecta un mock del servicio de cursos

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos a JSON

    @Test
    void testCrearCurso() throws Exception {
        // Curso simulado a retornar por el servicio
        Curso curso = new Curso(1L, "Curso de Vue.js", "Frontend avanzado", 18000);

        // Configura el servicio simulado para devolver el curso cuando se llama guardarCurso()
        when(cursoService.guardarCurso(any())).thenReturn(curso);

        // Realiza una petición POST al endpoint /cursos con el JSON del curso
        mockMvc.perform(post("/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(curso)))
               // Se espera un 200 OK y que el campo "nombre" del JSON devuelto sea "Curso de Vue.js"
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.nombre").value("Curso de Vue.js"));
    }

    @Test
    void testObtenerCursos() throws Exception {
        // Lista simulada devuelta por el servicio
        Curso curso = new Curso(1L, "Curso de Angular", "Frontend", 15000);
        when(cursoService.obtenerTodos()).thenReturn(List.of(curso));

        // Se realiza un GET al endpoint /cursos
        mockMvc.perform(get("/cursos"))
               // Se espera un 200 OK y que el primer curso en la lista se llame "Curso de Angular"
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].nombre").value("Curso de Angular"));
    }

    @Test
    void testObtenerCursoPorId_Existe() throws Exception {
        // Curso que será retornado si existe
        Curso curso = new Curso(1L, "Docker", "Contenedores", 13000);
        when(cursoService.obtenerPorId(1L)).thenReturn(Optional.of(curso));

        // GET /cursos/1 debería devolver el curso con nombre "Docker"
        mockMvc.perform(get("/cursos/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.nombre").value("Docker"));
    }

    @Test
    void testObtenerCursoPorId_NoExiste() throws Exception {
        // Simula que no se encuentra el curso
        when(cursoService.obtenerPorId(99L)).thenReturn(Optional.empty());

        // Se espera una respuesta 404 Not Found
        mockMvc.perform(get("/cursos/99"))
               .andExpect(status().isNotFound());
    }

    @Test
    void testActualizarCurso_Existe() throws Exception {
        // Curso original existente
        Curso original = new Curso(1L, "JavaScript", "Intro", 10000);
        // Curso actualizado con nuevos valores
        Curso actualizado = new Curso(1L, "JavaScript Avanzado", "DOM y más", 14000);

        // Simula que el curso existe y luego se guarda
        when(cursoService.obtenerPorId(1L)).thenReturn(Optional.of(original));
        when(cursoService.guardarCurso(any())).thenReturn(actualizado);

        // PUT /cursos/1 debería actualizar el curso y devolver el nuevo nombre
        mockMvc.perform(put("/cursos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.nombre").value("JavaScript Avanzado"));
    }

    @Test
    void testActualizarCurso_NoExiste() throws Exception {
        // Curso que se intenta actualizar, pero no existe
        Curso actualizado = new Curso(1L, "Python", "Intro", 9000);
        when(cursoService.obtenerPorId(1L)).thenReturn(Optional.empty());

        // Se espera 404 al intentar actualizar curso inexistente
        mockMvc.perform(put("/cursos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
               .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarCurso_Existe() throws Exception {
        // Simula una eliminación exitosa
        when(cursoService.eliminarCurso(1L)).thenReturn(true);

        // DELETE /cursos/1 debe responder con 204 No Content
        mockMvc.perform(delete("/cursos/1"))
               .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarCurso_NoExiste() throws Exception {
        // Simula intento de eliminar un curso que no existe
        when(cursoService.eliminarCurso(99L)).thenReturn(false);

        // Se espera 404 Not Found
        mockMvc.perform(delete("/cursos/99"))
               .andExpect(status().isNotFound());
    }
}