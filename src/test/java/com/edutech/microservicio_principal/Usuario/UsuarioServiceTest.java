package com.edutech.microservicio_principal.Usuario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        // Se crea un mock del repositorio y se inyecta en el servicio
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioService = new UsuarioService(usuarioRepository);
    }

    @Test
    void testListarUsuarios() {
        // Prepara una lista de usuarios simulados
        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario1, usuario2));

        // Ejecuta el método del servicio
        List<Usuario> usuarios = usuarioService.listarUsuarios();

        // Verifica que la lista tiene el tamaño esperado y se llamó a findAll()
        assertEquals(2, usuarios.size());
        verify(usuarioRepository).findAll();
    }

    @Test
    void testObtenerUsuarioPorId_Existe() {
        // Crea un usuario y configura el mock para retornarlo al buscar por ID
        Usuario usuario = new Usuario();
        usuario.setNombres("Elena");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.obtenerUsuarioPorId(1L);

        // Verifica que se obtuvo correctamente el usuario esperado
        assertTrue(resultado.isPresent());
        assertEquals("Elena", resultado.get().getNombres());
    }

    @Test
    void testObtenerUsuarioPorId_NoExiste() {
        // Simula que el ID buscado no existe
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioService.obtenerUsuarioPorId(99L);

        // Se espera que el resultado sea vacío
        assertFalse(resultado.isPresent());
    }

    @Test
    void testGuardarUsuario() {
        // Crea un usuario nuevo
        Usuario usuario = new Usuario();
        usuario.setCorreo("nuevo@usuario.com");

        // Simula que el repositorio guarda y retorna el mismo usuario
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario guardado = usuarioService.guardarUsuario(usuario);

        // Verifica que se retorna el usuario guardado
        assertEquals("nuevo@usuario.com", guardado.getCorreo());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void testActualizarUsuario_Existe() {
        // Crea usuario original y actualizado
        Usuario original = new Usuario(1L, "Pedro", "Lagos", "pedro@correo.com", "clave1", "ESTUDIANTE");
        Usuario nuevo = new Usuario(1L, "Pedro Pablo", "Lagos", "pedropablo@correo.com", "clave2", "ADMIN");

        // Simula que se encuentra al usuario original y luego se guarda el actualizado
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(original));
        when(usuarioRepository.save(any())).thenReturn(nuevo);

        Usuario resultado = usuarioService.actualizarUsuario(1L, nuevo);

        // Verifica que los valores se actualizan correctamente
        assertEquals("Pedro Pablo", resultado.getNombres());
        assertEquals("pedropablo@correo.com", resultado.getCorreo());
        verify(usuarioRepository).save(any());
    }

    @Test
    void testActualizarUsuario_NoExiste() {
        Usuario nuevo = new Usuario(1L, "Fantasma", "Desconocido", "ghost@null.com", "nulo", "NINGUNO");

        // Simula que el usuario no existe
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        // Se espera excepción al intentar actualizar usuario no encontrado
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> usuarioService.actualizarUsuario(1L, nuevo));

        assertTrue(ex.getMessage().contains("Usuario no encontrado"));
    }

    @Test
    void testEliminarUsuario_Existe() {
        // Simula que el usuario con ID 2 existe
        when(usuarioRepository.existsById(2L)).thenReturn(true);

        // Ejecuta la eliminación
        usuarioService.eliminarUsuario(2L);

        // Verifica que se llamó a deleteById con el ID correcto
        verify(usuarioRepository).deleteById(2L);
    }

    @Test
    void testEliminarUsuario_NoExiste() {
        // Simula que no existe el ID
        when(usuarioRepository.existsById(404L)).thenReturn(false);

        // Verifica que se lanza excepción al intentar eliminar
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> usuarioService.eliminarUsuario(404L));

        assertTrue(ex.getMessage().contains("Usuario no encontrado"));
    }
}