package com.edutech.microservicio_principal.Usuario;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // Ejecuta pruebas con una base de datos en memoria H2 y configuración JPA real
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository; // Repositorio real inyectado en el test

    @Test
    void testGuardarYBuscarUsuario() {
        // Crear y guardar un usuario en la base de datos
        Usuario usuario = new Usuario(null, "Nicolás", "Valdés", "nico@example.com", "clave123", "ESTUDIANTE");
        usuarioRepository.save(usuario);

        // Buscar por ID
        Optional<Usuario> encontrado = usuarioRepository.findById(usuario.getId());

        // Verificar que se haya encontrado y que los datos coincidan
        assertTrue(encontrado.isPresent());
        assertEquals("Nicolás", encontrado.get().getNombres());
        assertEquals("nico@example.com", encontrado.get().getCorreo());
    }

    @Test
    void testFindByNombres_Existente() {
        // Guardar un usuario con nombre único
        Usuario usuario = new Usuario(null, "Valentina", "Muñoz", "valen@example.com", "clave456", "ADMIN");
        usuarioRepository.save(usuario);

        // Buscar por nombre usando el método customizado
        Optional<Usuario> encontrado = usuarioRepository.findByNombres("Valentina");

        // Verificar que se encuentre correctamente
        assertTrue(encontrado.isPresent());
        assertEquals("valen@example.com", encontrado.get().getCorreo());
    }

    @Test
    void testFindByNombres_NoExistente() {
        // Intentar encontrar un nombre que no está en la base
        Optional<Usuario> resultado = usuarioRepository.findByNombres("Fantasma");

        // Debería retornar vacío
        assertFalse(resultado.isPresent());
    }
}