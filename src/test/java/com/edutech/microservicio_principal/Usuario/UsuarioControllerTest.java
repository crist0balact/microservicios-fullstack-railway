package com.edutech.microservicio_principal.Usuario;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class) // Carga solo el contexto del controlador
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc; // Permite simular peticiones HTTP al endpoint

    @MockBean
    private UsuarioService usuarioService; // Se mockea el servicio inyectado en el controlador

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos a JSON y viceversa

    @Test
    void testGuardarUsuario() throws Exception {
        Usuario usuario = new Usuario(1L, "Mario", "Lopez", "mario@example.com", "clave123", "ESTUDIANTE");

        // Simulamos que al guardar, el servicio retorna el mismo usuario
        Mockito.when(usuarioService.guardarUsuario(any(Usuario.class))).thenReturn(usuario);

        // POST /usuarios con un JSON del usuario → esperamos 200 y nombre correcto
        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.nombres").value("Mario"));
    }

    @Test
    void testObtenerTodos() throws Exception {
        Usuario usuario = new Usuario(1L, "Lucía", "Pérez", "lucia@example.com", "clave123", "ADMIN");
        Mockito.when(usuarioService.listarUsuarios()).thenReturn(List.of(usuario));

        // GET /usuarios → esperamos lista con "Lucía"
        mockMvc.perform(get("/usuarios"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].nombres").value("Lucía"));
    }

    @Test
    void testObtenerPorId_Existe() throws Exception {
        Usuario usuario = new Usuario(1L, "Carlos", "Díaz", "carlos@example.com", "clave123", "ESTUDIANTE");
        Mockito.when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(Optional.of(usuario));

        // GET /usuarios/1 → esperamos 200 OK con "Carlos"
        mockMvc.perform(get("/usuarios/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.nombres").value("Carlos"));
    }

    @Test
    void testObtenerPorId_NoExiste() throws Exception {
        Mockito.when(usuarioService.obtenerUsuarioPorId(99L)).thenReturn(Optional.empty());

        // GET /usuarios/99 → esperamos 404 Not Found
        mockMvc.perform(get("/usuarios/99"))
               .andExpect(status().isNotFound());
    }

    @Test
    void testActualizarUsuario_Existe() throws Exception {
        Usuario original = new Usuario(1L, "Ana", "Soto", "ana@example.com", "clave123", "ESTUDIANTE");
        Usuario actualizado = new Usuario(1L, "Ana María", "Soto", "ana@example.com", "nuevaclave", "ADMIN");

        Mockito.when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(Optional.of(original));
        Mockito.when(usuarioService.guardarUsuario(any())).thenReturn(actualizado);

        // PUT /usuarios/1 → esperamos 200 OK con el nuevo nombre "Ana María"
        mockMvc.perform(put("/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.nombres").value("Ana María"));
    }

    @Test
    void testActualizarUsuario_NoExiste() throws Exception {
        Usuario actualizado = new Usuario(1L, "Roberto", "Torres", "roberto@example.com", "clave", "ESTUDIANTE");
        Mockito.when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(Optional.empty());

        // PUT /usuarios/1 → no se encuentra usuario → 404
        mockMvc.perform(put("/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
               .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarUsuario() throws Exception {
        // DELETE /usuarios/1 simplemente ejecuta el método sin retornar nada
        mockMvc.perform(delete("/usuarios/1"))
               .andExpect(status().isOk()); // Devuelve 200 OK (porque es void)
    }
}