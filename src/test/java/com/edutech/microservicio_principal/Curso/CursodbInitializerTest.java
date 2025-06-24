package com.edutech.microservicio_principal.Curso;

import org.junit.jupiter.api.Test;

/*import java.util.List;*/

import static org.mockito.Mockito.*;

class CursodbInitializerTest {

    @Test
    void testCargaCursosSiRepoVacio() {
        // Simular repositorio de Curso usando Mockito
        CursoRepository cursoRepository = mock(CursoRepository.class);

        // Simular que el repositorio está vacío (sin cursos en BD)
        when(cursoRepository.count()).thenReturn(0L);

        // Crear instancia del inicializador con el mock
        CursodbInitializer initializer = new CursodbInitializer(cursoRepository);
        initializer.run(); // Ejecutar el método que debe poblar la base

        // Verificar que saveAll() fue invocado una vez con cualquier lista de cursos
        verify(cursoRepository, times(1)).saveAll(any());
    }

    @Test
    void testNoHaceNadaSiYaHayCursos() {
        // Crear mock del repositorio
        CursoRepository cursoRepository = mock(CursoRepository.class);

        // Simular que ya hay cursos guardados en la base
        when(cursoRepository.count()).thenReturn(3L);

        // Ejecutar el initializer
        CursodbInitializer initializer = new CursodbInitializer(cursoRepository);
        initializer.run();

        // Verificar que saveAll() no fue llamado (ya había cursos)
        verify(cursoRepository, never()).saveAll(any());
    }
}