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
        claseRepository = mock(ClaseRepository.class);
        claseService = new ClaseService(claseRepository);
    }

    @Test
    void testGuardarClase() {
        Clase clase = new Clase();
        clase.setNombre("Backend con Spring");

        when(claseRepository.save(clase)).thenReturn(clase);

        Clase resultado = claseService.guardarClase(clase);

        assertNotNull(resultado);
        assertEquals("Backend con Spring", resultado.getNombre());
        verify(claseRepository, times(1)).save(clase);
    }

    @Test
    void testObtenerClasePorIdExistente() {
        Clase clase = new Clase();
        clase.setNombre("DevOps");

        when(claseRepository.findById(1L)).thenReturn(Optional.of(clase));

        Optional<Clase> resultado = claseService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("DevOps", resultado.get().getNombre());
    }

    @Test
    void testObtenerClasePorIdNoExistente() {
        when(claseRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Clase> resultado = claseService.obtenerPorId(99L);

        assertFalse(resultado.isPresent());
    }

    @Test
    void testObtenerTodasLasClases() {
        Clase clase1 = new Clase();
        Clase clase2 = new Clase();
        when(claseRepository.findAll()).thenReturn(List.of(clase1, clase2));

        List<Clase> clases = claseService.obtenerTodas();

        assertEquals(2, clases.size());
        verify(claseRepository).findAll();
    }

    @Test
    void testEliminarClaseExistente() {
        when(claseRepository.existsById(1L)).thenReturn(true);

        boolean resultado = claseService.eliminarClase(1L);

        assertTrue(resultado);
        verify(claseRepository).deleteById(1L);
    }

    @Test
    void testEliminarClaseInexistente() {
        when(claseRepository.existsById(99L)).thenReturn(false);

        boolean resultado = claseService.eliminarClase(99L);

        assertFalse(resultado);
        verify(claseRepository, never()).deleteById(99L);
    }
}