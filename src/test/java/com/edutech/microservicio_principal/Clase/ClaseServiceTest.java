package com.edutech.microservicio_principal.Clase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/*import org.mockito.Mockito;*/

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClaseServiceTest {

    private ClaseRepository claseRepository;
    private ClaseService claseService;

    @BeforeEach
    void setUp() {
        // Se crea un mock del repositorio y se inyecta en el servicio antes de cada test
        claseRepository = mock(ClaseRepository.class);
        claseService = new ClaseService(claseRepository);
    }

    @Test
    void testGuardarClase() {
        // Se simula una clase de entrada
        Clase clase = new Clase();
        clase.setNombre("Backend con Spring");

        // El mock debe devolver la misma clase al guardarla
        when(claseRepository.save(clase)).thenReturn(clase);

        // Llamada al servicio
        Clase resultado = claseService.guardarClase(clase);

        // Verificación de resultado y que el repositorio fue invocado correctamente
        assertNotNull(resultado);
        assertEquals("Backend con Spring", resultado.getNombre());
        verify(claseRepository, times(1)).save(clase);
    }

    @Test
    void testObtenerClasePorIdExistente() {
        // Se prepara una clase ficticia que será retornada por el mock
        Clase clase = new Clase();
        clase.setNombre("DevOps");

        when(claseRepository.findById(1L)).thenReturn(Optional.of(clase));

        // Llamado al servicio y verificación
        Optional<Clase> resultado = claseService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("DevOps", resultado.get().getNombre());
    }

    @Test
    void testObtenerClasePorIdNoExistente() {
        // Simula que no se encuentra ninguna clase con el ID especificado
        when(claseRepository.findById(99L)).thenReturn(Optional.empty());

        // El resultado debe ser vacío
        Optional<Clase> resultado = claseService.obtenerPorId(99L);

        assertFalse(resultado.isPresent());
    }

    @Test
    void testObtenerTodasLasClases() {
        // Simula que el repositorio retorna dos clases
        Clase clase1 = new Clase();
        Clase clase2 = new Clase();
        when(claseRepository.findAll()).thenReturn(List.of(clase1, clase2));

        // Llamada al servicio
        List<Clase> clases = claseService.obtenerTodas();

        // Verificación de tamaño y uso del repositorio
        assertEquals(2, clases.size());
        verify(claseRepository).findAll();
    }

    @Test
    void testEliminarClaseExistente() {
        // Simula que la clase con ID 1 sí existe
        when(claseRepository.existsById(1L)).thenReturn(true);

        // Se espera que se elimine con éxito
        boolean resultado = claseService.eliminarClase(1L);

        assertTrue(resultado);
        verify(claseRepository).deleteById(1L);
    }

    @Test
    void testEliminarClaseInexistente() {
        // Simula que no existe la clase con ese ID
        when(claseRepository.existsById(99L)).thenReturn(false);

        // No debería eliminar nada, y se espera false
        boolean resultado = claseService.eliminarClase(99L);

        assertFalse(resultado);
        verify(claseRepository, never()).deleteById(99L);
    }
}