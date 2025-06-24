package com.edutech.microservicio_principal.Usuario;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class UsuariodbInitializerTest {

    @Test
    void testCargaUsuariosSiBaseVacia() {
        // Se crea un mock del repositorio de usuarios
        UsuarioRepository mockRepo = mock(UsuarioRepository.class);

        // Simula que no hay usuarios en la base de datos
        when(mockRepo.count()).thenReturn(0L);

        // Se crea una instancia del initializer con el mock y se ejecuta
        UsuariodbInitializer initializer = new UsuariodbInitializer(mockRepo);
        initializer.run();

        // Verifica que se llamó al método saveAll() una vez para insertar usuarios
        verify(mockRepo, times(1)).saveAll(any());
    }

    @Test
    void testNoCargaUsuariosSiYaHayDatos() {
        // Se crea un mock del repositorio
        UsuarioRepository mockRepo = mock(UsuarioRepository.class);

        // Simula que ya existen usuarios
        when(mockRepo.count()).thenReturn(5L);

        // Se ejecuta el initializer
        UsuariodbInitializer initializer = new UsuariodbInitializer(mockRepo);
        initializer.run();

        // Verifica que no se llamó a saveAll() si ya había datos
        verify(mockRepo, never()).saveAll(any());
    }
}