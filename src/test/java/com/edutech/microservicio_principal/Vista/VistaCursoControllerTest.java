package com.edutech.microservicio_principal.Vista;

import com.edutech.microservicio_principal.Curso.Curso;
import com.edutech.microservicio_principal.Curso.CursoService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VistaCursoController.class) // Carga sólo el controlador VistaCursoController
class VistaCursoControllerTest {

    @Autowired
    private MockMvc mockMvc; // Permite simular peticiones HTTP

    @MockBean
    private CursoService cursoService; // Simula el servicio real inyectado en el controlador

    @Test
    void testMostrarVistaCursos() throws Exception {
        // Se preparan cursos simulados
        Curso curso1 = new Curso(1L, "Java", "Backend", 15000);
        Curso curso2 = new Curso(2L, "Angular", "Frontend", 12000);

        // Se configura el comportamiento del mock para retornar la lista esperada
        when(cursoService.obtenerTodos()).thenReturn(List.of(curso1, curso2));

        // Se hace la solicitud GET al endpoint y se verifica:
        // - que devuelve HTTP 200
        // - que renderiza la vista llamada "vista-cursos"
        // - que agrega al modelo un atributo llamado "cursos"
        mockMvc.perform(get("/vista-cursos"))
               .andExpect(status().isOk())
               .andExpect(view().name("vista-cursos"))
               .andExpect(model().attributeExists("cursos"));
    }
}