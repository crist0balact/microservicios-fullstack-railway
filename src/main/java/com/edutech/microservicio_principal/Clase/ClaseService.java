package com.edutech.microservicio_principal.Clase;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Service
public class ClaseService {

    private final ClaseRepository claseRepository;

    @Autowired
    public ClaseService(ClaseRepository claseRepository) {
        this.claseRepository = claseRepository;
    }

    public Clase guardarClase(Clase clase) {
        return claseRepository.save(clase);
    }

    public List<Clase> obtenerTodas() {
        return claseRepository.findAll();
    }
    
    public List<Clase> obtenerClasesPorCurso(String nombreCurso) {
    return claseRepository.findByCursoNombre(nombreCurso); // Consulta el repositorio para retornar una lista de objetos Clase
}

    public Optional<Clase> obtenerPorId(Long id) {
        return claseRepository.findById(id);
    }

    public boolean eliminarClase(Long id) {
        if (claseRepository.existsById(id)) {
            claseRepository.deleteById(id);
            return true;
        }
        return false;
    }
}