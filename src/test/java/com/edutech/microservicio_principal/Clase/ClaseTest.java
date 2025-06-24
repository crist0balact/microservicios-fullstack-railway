package com.edutech.microservicio_principal.Clase;

import com.edutech.microservicio_principal.Curso.Curso;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClaseTest {

    @Test
    void testConstructorYGetters() {
        // Crear curso asociado
        Curso curso = new Curso();
        curso.setNombre("Matemáticas");

        // Crear instancia de Clase usando constructor completo
        Clase clase = new Clase(1L, "Álgebra", "Nivel intermedio", 25, "08:00 AM", curso);

        // Verificar que todos los getters funcionan y devuelven los valores esperados
        assertEquals(1L, clase.getId());
        assertEquals("Álgebra", clase.getNombre());
        assertEquals("Nivel intermedio", clase.getDescripcion());
        assertEquals(25, clase.getCuposDisponibles());
        assertEquals("08:00 AM", clase.getHorario());
        assertEquals("Matemáticas", clase.getCurso().getNombre()); // Chequeo del objeto anidado
    }

    @Test
    void testSetters() {
        // Crear curso asociado
        Curso curso = new Curso();
        curso.setNombre("Física");

        // Instanciar Clase sin usar constructor, solo setters
        Clase clase = new Clase();
        clase.setId(2L);
        clase.setNombre("Mecánica");
        clase.setDescripcion("Avanzado");
        clase.setCuposDisponibles(40);
        clase.setHorario("10:30 AM");
        clase.setCurso(curso);

        // Verificar que los valores seteados se recuperan correctamente
        assertEquals(2L, clase.getId());
        assertEquals("Mecánica", clase.getNombre());
        assertEquals("Avanzado", clase.getDescripcion());
        assertEquals(40, clase.getCuposDisponibles());
        assertEquals("10:30 AM", clase.getHorario());
        assertEquals("Física", clase.getCurso().getNombre());
    }

    @Test
    void testToString() {
        // Crear curso asociado
        Curso curso = new Curso();
        curso.setNombre("Historia");

        // Crear objeto Clase con valores que se mostrarán en toString
        Clase clase = new Clase(3L, "Edad Media", "Curso general", 20, "3:00 PM", curso);

        // Ejecutar toString() y verificar que contiene información clave
        String toStringResult = clase.toString();
        assertTrue(toStringResult.contains("Edad Media"));     // Valida que incluya el nombre de la clase
        assertTrue(toStringResult.contains("Historia"));       // Valida que incluya el nombre del curso
    }
}