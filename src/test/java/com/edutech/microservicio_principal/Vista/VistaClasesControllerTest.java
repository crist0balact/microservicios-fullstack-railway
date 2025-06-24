package com.edutech.microservicio_principal.Vista;

import com.edutech.microservicio_principal.Clase.ClaseService;
import com.edutech.microservicio_principal.Curso.Curso;
import com.edutech.microservicio_principal.Curso.CursoRepository;
import com.edutech.microservicio_principal.Usuario.Usuario;
import com.edutech.microservicio_principal.Usuario.UsuarioRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VistaClasesController.class)
class VistaClasesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClaseService claseService;

    @MockBean
    private CursoRepository cursoRepository;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Test
    void testMostrarClasesPorCurso() throws Exception {
        // Datos de entrada para simular la sesión y la ruta
        String nombreCurso = "Curso de Java";
        String usuarioNombre = "Cristóbal";

        // Simula una clase, curso y usuario que serán retornados por los mocks
        Curso curso = new Curso();
        curso.setNombre(nombreCurso);

        Usuario usuario = new Usuario();
        usuario.setNombres(usuarioNombre);

        when(claseService.obtenerClasesPorCurso(nombreCurso)).thenReturn(List.of());
        when(cursoRepository.findByNombre(nombreCurso)).thenReturn(Optional.of(curso));
        when(usuarioRepository.findByNombres(usuarioNombre)).thenReturn(Optional.of(usuario));

        // Construye la solicitud GET con atributo de sesión simulado
        MockHttpServletRequestBuilder request = get("/vista-clases/{nombreCurso}", nombreCurso)
                .sessionAttr("usuarioNombre", usuarioNombre);

        // Ejecuta la solicitud y verifica:
        // - que la vista retornada sea "vista-clases"
        // - que el modelo incluya los atributos esperados
        mockMvc.perform(request)
               .andExpect(status().isOk())
               .andExpect(view().name("vista-clases"))
               .andExpect(model().attributeExists("nombreCurso"))
               .andExpect(model().attributeExists("clases"))
               .andExpect(model().attributeExists("curso"))
               .andExpect(model().attributeExists("usuario"));
    }
}