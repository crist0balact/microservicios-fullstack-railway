package com.edutech.microservicio_principal.Clase;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClaseController.class)
class ClaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClaseService claseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCrearClase() throws Exception {
        // Se crea una clase de ejemplo con valores de prueba
        Clase clase = new Clase();
        clase.setNombre("Filosofía");
        clase.setDescripcion("Clase de introducción");
        clase.setCuposDisponibles(20);
        clase.setHorario("8:00 AM");

        // Se configura el mock del servicio para que devuelva la clase al guardarla
        Mockito.when(claseService.guardarClase(any(Clase.class))).thenReturn(clase);

        // Se realiza una petición POST /clases y se valida que el nombre sea el esperado
        mockMvc.perform(post("/clases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clase)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Filosofía"));
    }

    @Test
    void testObtenerClases() throws Exception {
        // Se simula una lista con una clase
        Clase clase = new Clase();
        clase.setNombre("Historia");

        Mockito.when(claseService.obtenerTodas()).thenReturn(List.of(clase));

        // Se realiza una petición GET /clases y se valida que devuelva la lista correctamente
        mockMvc.perform(get("/clases"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Historia"));
    }

    @Test
    void testObtenerClasePorId_Existente() throws Exception {
        // Se simula que se encuentra una clase con ID 1
        Clase clase = new Clase();
        clase.setNombre("Matemáticas");

        Mockito.when(claseService.obtenerPorId(1L)).thenReturn(Optional.of(clase));

        // GET /clases/1 debe devolver status 200 y el nombre correspondiente
        mockMvc.perform(get("/clases/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Matemáticas"));
    }

    @Test
    void testObtenerClasePorId_NoEncontrada() throws Exception {
        // Simula que no se encuentra ninguna clase con ID 99
        Mockito.when(claseService.obtenerPorId(99L)).thenReturn(Optional.empty());

        // GET /clases/99 debe devolver 404 Not Found
        mockMvc.perform(get("/clases/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarClase_Exitosa() throws Exception {
        // Simula eliminación exitosa
        Mockito.when(claseService.eliminarClase(1L)).thenReturn(true);

        // DELETE /clases/1 debe devolver 204 No Content
        mockMvc.perform(delete("/clases/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarClase_NoEncontrada() throws Exception {
        // Simula intento de eliminar clase inexistente
        Mockito.when(claseService.eliminarClase(99L)).thenReturn(false);

        // DELETE /clases/99 debe devolver 404 Not Found
        mockMvc.perform(delete("/clases/99"))
                .andExpect(status().isNotFound());
    }
}