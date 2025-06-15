package com.edutech.microservicio_principal.Clase;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; // Se añade el import de List para usar listas

public interface ClaseRepository extends JpaRepository<Clase, Long> {
    List<Clase> findByCursoNombre(String nombreCurso); // Consulta para buscar las clases donde curso.nombre coincida con nombreCurso
}