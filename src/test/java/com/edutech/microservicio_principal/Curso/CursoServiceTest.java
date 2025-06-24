package com.edutech.microservicio_principal.Curso;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CursoServiceTest {

    private CursoRepository cursoRepository;
    private CursoService cursoService;

    @BeforeEach
    void setUp() {
        // Se configura un mock del repositorio y se inyecta en el servicio antes de cada test
        cursoRepository = mock(CursoRepository.class);
        cursoService = new CursoService(cursoRepository);
    }

    @Test
    void testGuardarCurso() {
        // Simula un curso a guardar
        Curso curso = new Curso(null, "Curso de React", "Framework frontend", 20000);
        when(cursoRepository.save(curso)).thenReturn(curso);

        // Ejecuta el método a probar
        Curso resultado = cursoService.guardarCurso(curso);

        // Verifica que se guardó correctamente
        assertNotNull(resultado);
        assertEquals("Curso de React", resultado.getNombre());
        verify(cursoRepository).save(curso);
    }

    @Test
    void testObtenerTodos() {
        // Simula una lista de cursos
        Curso curso1 = new Curso(null, "Curso A", "Desc A", 10000);
        Curso curso2 = new Curso(null, "Curso B", "Desc B", 12000);
        when(cursoRepository.findAll()).thenReturn(List.of(curso1, curso2));

        // Ejecuta el método del servicio
        List<Curso> cursos = cursoService.obtenerTodos();

        // Verifica que se recuperen correctamente
        assertEquals(2, cursos.size());
        verify(cursoRepository).findAll();
    }

    @Test
    void testObtenerPorIdExistente() {
        // Simula que encuentra un curso por ID
        Curso curso = new Curso(1L, "Curso de Node", "Backend JS", 18000);
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));

        // Ejecuta la búsqueda por ID
        Optional<Curso> resultado = cursoService.obtenerPorId(1L);

        // Verifica que se encuentra y coincide el nombre
        assertTrue(resultado.isPresent());
        assertEquals("Curso de Node", resultado.get().getNombre());
    }

    @Test
    void testObtenerPorIdNoExiste() {
        // Simula que el ID no existe
        when(cursoRepository.findById(99L)).thenReturn(Optional.empty());

        // Se espera Optional vacío
        Optional<Curso> resultado = cursoService.obtenerPorId(99L);

        assertFalse(resultado.isPresent());
    }

    @Test
    void testEliminarCurso_Existe() {
        // Simula que el ID existe y puede eliminarse
        when(cursoRepository.existsById(1L)).thenReturn(true);

        // Llama al método y verifica resultado
        boolean eliminado = cursoService.eliminarCurso(1L);

        assertTrue(eliminado);
        verify(cursoRepository).deleteById(1L);
    }

    @Test
    void testEliminarCurso_NoExiste() {
        // Simula que el ID no existe
        when(cursoRepository.existsById(77L)).thenReturn(false);

        // Se espera que no se elimine
        boolean eliminado = cursoService.eliminarCurso(77L);

        assertFalse(eliminado);
        verify(cursoRepository, never()).deleteById(anyLong());
    }
}