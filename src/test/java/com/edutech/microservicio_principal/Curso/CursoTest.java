package com.edutech.microservicio_principal.Curso;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CursoTest {

    @Test
    void testConstructorYGettersSetters() {
        // Se crea un objeto Curso usando el constructor completo
        Curso curso = new Curso(1L, "Curso de Java", "Backend completo", 15000);

        // Se validan los valores iniciales obtenidos por los getters
        assertEquals(1L, curso.getId());
        assertEquals("Curso de Java", curso.getNombre());
        assertEquals("Backend completo", curso.getDescripcion());
        assertEquals(15000, curso.getValorCurso());

        // Se prueba el uso de un setter para modificar la descripción
        curso.setDescripcion("Java desde cero");

        // Se verifica que el cambio en la descripción se haya aplicado correctamente
        assertEquals("Java desde cero", curso.getDescripcion());
    }

    @Test
    void testToString() {
        // Se crea un curso con valores conocidos
        Curso curso = new Curso(2L, "React", "Frontend", 12000);

        // Se obtiene el resultado del método toString()
        String resultado = curso.toString();

        // Se comprueba que el resultado incluye partes clave del contenido del objeto
        assertTrue(resultado.contains("React"));
        assertTrue(resultado.contains("Frontend"));
    }
}