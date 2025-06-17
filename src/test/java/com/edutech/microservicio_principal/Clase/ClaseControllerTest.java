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
        Clase clase = new Clase();
        clase.setNombre("Filosofía");
        clase.setDescripcion("Clase de introducción");
        clase.setCuposDisponibles(20);
        clase.setHorario("8:00 AM");

        Mockito.when(claseService.guardarClase(any(Clase.class))).thenReturn(clase);

        mockMvc.perform(post("/clases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clase)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Filosofía"));
    }

    @Test
    void testObtenerClases() throws Exception {
        Clase clase = new Clase();
        clase.setNombre("Historia");

        Mockito.when(claseService.obtenerTodas()).thenReturn(List.of(clase));

        mockMvc.perform(get("/clases"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Historia"));
    }

    @Test
    void testObtenerClasePorId_Existente() throws Exception {
        Clase clase = new Clase();
        clase.setNombre("Matemáticas");

        Mockito.when(claseService.obtenerPorId(1L)).thenReturn(Optional.of(clase));

        mockMvc.perform(get("/clases/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Matemáticas"));
    }

    @Test
    void testObtenerClasePorId_NoEncontrada() throws Exception {
        Mockito.when(claseService.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/clases/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarClase_Exitosa() throws Exception {
        Mockito.when(claseService.eliminarClase(1L)).thenReturn(true);

        mockMvc.perform(delete("/clases/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarClase_NoEncontrada() throws Exception {
        Mockito.when(claseService.eliminarClase(99L)).thenReturn(false);

        mockMvc.perform(delete("/clases/99"))
                .andExpect(status().isNotFound());
    }
}
