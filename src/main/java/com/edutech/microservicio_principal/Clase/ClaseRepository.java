package com.edutech.microservicio_principal.Clase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {

    // buscar clases según el nombre del curso asociado
    List<Clase> findByCursoNombre(String nombre);
}