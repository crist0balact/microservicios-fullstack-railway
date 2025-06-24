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
        cursoRepository = mock(CursoRepository.class);
        cursoService = new CursoService(cursoRepository);
    }

    @Test
    void testGuardarCurso() {
        Curso curso = new Curso(null, "Curso de React", "Framework frontend", 20000);
        when(cursoRepository.save(curso)).thenReturn(curso);

        Curso resultado = cursoService.guardarCurso(curso);

        assertNotNull(resultado);
        assertEquals("Curso de React", resultado.getNombre());
        verify(cursoRepository).save(curso);
    }

    @Test
    void testObtenerTodos() {
        Curso curso1 = new Curso(null, "Curso A", "Desc A", 10000);
        Curso curso2 = new Curso(null, "Curso B", "Desc B", 12000);

        when(cursoRepository.findAll()).thenReturn(List.of(curso1, curso2));

        List<Curso> cursos = cursoService.obtenerTodos();

        assertEquals(2, cursos.size());
        verify(cursoRepository).findAll();
    }

    @Test
    void testObtenerPorIdExistente() {
        Curso curso = new Curso(1L, "Curso de Node", "Backend JS", 18000);
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));

        Optional<Curso> resultado = cursoService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Curso de Node", resultado.get().getNombre());
    }

    @Test
    void testObtenerPorIdNoExiste() {
        when(cursoRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Curso> resultado = cursoService.obtenerPorId(99L);

        assertFalse(resultado.isPresent());
    }

    @Test
    void testEliminarCurso_Existe() {
        when(cursoRepository.existsById(1L)).thenReturn(true);

        boolean eliminado = cursoService.eliminarCurso(1L);

        assertTrue(eliminado);
        verify(cursoRepository).deleteById(1L);
    }

    @Test
    void testEliminarCurso_NoExiste() {
        when(cursoRepository.existsById(77L)).thenReturn(false);

        boolean eliminado = cursoService.eliminarCurso(77L);

        assertFalse(eliminado);
        verify(cursoRepository, never()).deleteById(anyLong());
    }
}
