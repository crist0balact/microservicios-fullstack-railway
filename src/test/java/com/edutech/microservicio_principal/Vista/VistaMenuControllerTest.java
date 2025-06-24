package com.edutech.microservicio_principal.Vista;

import com.edutech.microservicio_principal.Curso.Curso;
import com.edutech.microservicio_principal.Curso.CursoRepository;
import com.edutech.microservicio_principal.Usuario.Usuario;
import com.edutech.microservicio_principal.Usuario.UsuarioRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;
/*import org.springframework.mock.web.MockHttpSession;*/

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VistaMenuController.class)
class VistaMenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private CursoRepository cursoRepository;

    @Test
    void testMostrarFormulario() throws Exception {
        // GET /menu debe devolver la vista "menu" con el atributo "error" en null
        mockMvc.perform(get("/menu"))
               .andExpect(status().isOk())
               .andExpect(view().name("menu"))
               .andExpect(model().attribute("error", (Object) null));
    }

    @Test
    void testProcesarLogin_Exitoso() throws Exception {
        // Usuario de prueba con credenciales válidas
        Usuario usuario = new Usuario(1L, "Carlos", "Bravo", "carlos@correo.com", "12345678", "ESTUDIANTE");
        when(usuarioRepository.findAll()).thenReturn(new ArrayList<>(List.of(usuario)));

        // POST con credenciales válidas espera redirección a "bienvenida"
        mockMvc.perform(post("/menu")
                .param("correo", "carlos@correo.com")
                .param("contraseña", "12345678"))
               .andExpect(status().isOk())
               .andExpect(view().name("bienvenida"))
               .andExpect(model().attribute("usuarioNombre", "Carlos"));
    }

    @Test
    void testProcesarLogin_Invalido() throws Exception {
        // Usuario en el repo pero contraseña incorrecta
        Usuario usuario = new Usuario(1L, "Carlos", "Bravo", "carlos@correo.com", "12345678", "ESTUDIANTE");
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        mockMvc.perform(post("/menu")
                .param("correo", "carlos@correo.com")
                .param("contraseña", "malapass"))
               .andExpect(status().isOk())
               .andExpect(view().name("menu"))
               .andExpect(model().attribute("error", "Correo o contraseña inválidos"));
    }

    @Test
    void testMostrarBienvenida() throws Exception {
        // Simula una sesión donde ya está seteado el usuarioNombre
        mockMvc.perform(get("/bienvenida")
                .sessionAttr("usuarioNombre", "Laura"))
               .andExpect(status().isOk())
               .andExpect(view().name("bienvenida"))
               .andExpect(model().attribute("usuarioNombre", "Laura"));
    }

    @Test
    void testMostrarHorario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNombres("Laura");
        usuario.setCursosInscritos(List.of());

        when(usuarioRepository.findByNombres("Laura")).thenReturn(Optional.of(usuario));

        // GET /vista-horario con usuario en sesión devuelve cursosInscritos vacíos
        mockMvc.perform(get("/vista-horario").sessionAttr("usuarioNombre", "Laura"))
               .andExpect(status().isOk())
               .andExpect(view().name("vista-horario"))
               .andExpect(model().attributeExists("cursosInscritos"));
    }

    @Test
    void testInscribirseId() throws Exception {
        // Preparamos un usuario sin cursos inscritos y un curso para inscribir
        Usuario usuario = new Usuario();
        usuario.setNombres("Laura");
        usuario.setCursosInscritos(new ArrayList<>());

        Curso curso = new Curso(1L, "Java", "Curso básico", 10000);

        when(usuarioRepository.findByNombres("Laura")).thenReturn(Optional.of(usuario));
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));

        // Se espera redirección a /vista-horario tras inscripción exitosa
        mockMvc.perform(get("/inscribirse-id/1").sessionAttr("usuarioNombre", "Laura"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/vista-horario"));
    }

    @Test
    void testCancelarInscripcion() throws Exception {
        Curso curso = new Curso(1L, "Java", "Curso básico", 10000);

        Usuario usuario = new Usuario();
        usuario.setNombres("Laura");
        usuario.setCursosInscritos(new ArrayList<>(List.of(curso)));

        when(usuarioRepository.findByNombres("Laura")).thenReturn(Optional.of(usuario));
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));

        // Se espera redirección a /vista-horario después de cancelar inscripción
        mockMvc.perform(get("/cancelar-inscripcion/1").sessionAttr("usuarioNombre", "Laura"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/vista-horario"));
    }
}