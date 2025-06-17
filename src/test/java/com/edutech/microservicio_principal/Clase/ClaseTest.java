package com.edutech.microservicio_principal.Clase;

import com.edutech.microservicio_principal.Curso.Curso;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClaseTest {

    @Test
    void testConstructorYGetters() {
        Curso curso = new Curso();
        curso.setNombre("Matemáticas");

        Clase clase = new Clase(1L, "Álgebra", "Nivel intermedio", 25, "08:00 AM", curso);

        assertEquals(1L, clase.getId());
        assertEquals("Álgebra", clase.getNombre());
        assertEquals("Nivel intermedio", clase.getDescripcion());
        assertEquals(25, clase.getCuposDisponibles());
        assertEquals("08:00 AM", clase.getHorario());
        assertEquals("Matemáticas", clase.getCurso().getNombre());
    }

    @Test
    void testSetters() {
        Curso curso = new Curso();
        curso.setNombre("Física");

        Clase clase = new Clase();
        clase.setId(2L);
        clase.setNombre("Mecánica");
        clase.setDescripcion("Avanzado");
        clase.setCuposDisponibles(40);
        clase.setHorario("10:30 AM");
        clase.setCurso(curso);

        assertEquals(2L, clase.getId());
        assertEquals("Mecánica", clase.getNombre());
        assertEquals("Avanzado", clase.getDescripcion());
        assertEquals(40, clase.getCuposDisponibles());
        assertEquals("10:30 AM", clase.getHorario());
        assertEquals("Física", clase.getCurso().getNombre());
    }

    @Test
    void testToString() {
        Curso curso = new Curso();
        curso.setNombre("Historia");

        Clase clase = new Clase(3L, "Edad Media", "Curso general", 20, "3:00 PM", curso);

        String toStringResult = clase.toString();
        assertTrue(toStringResult.contains("Edad Media"));
        assertTrue(toStringResult.contains("Historia"));
    }
}
