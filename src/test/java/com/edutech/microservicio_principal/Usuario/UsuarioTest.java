package com.edutech.microservicio_principal.Usuario;

import com.edutech.microservicio_principal.Curso.Curso;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void testConstructorYGetters() {
        // Crear un usuario con constructor completo
        Usuario usuario = new Usuario(1L, "Ana", "Ramírez", "ana@example.com", "password123", "ESTUDIANTE");

        // Verificar que los valores se asignan correctamente desde el constructor
        assertEquals(1L, usuario.getId());
        assertEquals("Ana", usuario.getNombres());
        assertEquals("Ramírez", usuario.getApellidos());
        assertEquals("ana@example.com", usuario.getCorreo());
        assertEquals("password123", usuario.getContraseña());
        assertEquals("ESTUDIANTE", usuario.getRol());

        // Verificar que la lista de cursos inicialmente está vacía
        assertNotNull(usuario.getCursosInscritos());
        assertTrue(usuario.getCursosInscritos().isEmpty());
    }

    @Test
    void testSetters() {
        // Instanciar usuario sin datos y usar setters
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setNombres("Luis");
        usuario.setApellidos("Gómez");
        usuario.setCorreo("luis@example.com");
        usuario.setContraseña("clave123");
        usuario.setRol("ADMIN");

        // Verificar que los valores fueron asignados correctamente
        assertEquals(2L, usuario.getId());
        assertEquals("Luis", usuario.getNombres());
        assertEquals("Gómez", usuario.getApellidos());
        assertEquals("luis@example.com", usuario.getCorreo());
        assertEquals("clave123", usuario.getContraseña());
        assertEquals("ADMIN", usuario.getRol());
    }

    @Test
    void testManipularCursosInscritos() {
        // Crear un curso de prueba
        Curso curso = new Curso();
        curso.setNombre("Curso de Java");

        // Crear usuario y agregarle un curso a la lista
        Usuario usuario = new Usuario();
        usuario.setCursosInscritos(List.of(curso));

        // Verificar que el curso se encuentra en la lista
        assertEquals(1, usuario.getCursosInscritos().size());
        assertEquals("Curso de Java", usuario.getCursosInscritos().get(0).getNombre());
    }

    @Test
    void testToString() {
        // Crear usuario con datos de prueba
        Usuario usuario = new Usuario(3L, "Clara", "Soto", "clara@example.com", "segura123", "ESTUDIANTE");

        // Agregar un curso ficticio
        Curso curso = new Curso();
        usuario.setCursosInscritos(List.of(curso));

        // Ejecutar el método toString
        String resultado = usuario.toString();

        // Verificar que incluye información clave pero oculta la contraseña real
        assertTrue(resultado.contains("Clara"));
        assertTrue(resultado.contains("clara@example.com"));
        assertTrue(resultado.contains("********")); // La contraseña debe aparecer oculta
        assertTrue(resultado.contains("cursosInscritos=1")); // La lista de cursos debe reflejar el tamaño
    }
}