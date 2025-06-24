package com.edutech.microservicio_principal.Curso;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CursoTest {

    @Test
    void testConstructorYGettersSetters() {
        Curso curso = new Curso(1L, "Curso de Java", "Backend completo", 15000);

        assertEquals(1L, curso.getId());
        assertEquals("Curso de Java", curso.getNombre());
        assertEquals("Backend completo", curso.getDescripcion());
        assertEquals(15000, curso.getValorCurso());

        curso.setDescripcion("Java desde cero");
        assertEquals("Java desde cero", curso.getDescripcion());
    }

    @Test
    void testToString() {
        Curso curso = new Curso(2L, "React", "Frontend", 12000);
        String resultado = curso.toString();
        assertTrue(resultado.contains("React"));
        assertTrue(resultado.contains("Frontend"));
    }
}