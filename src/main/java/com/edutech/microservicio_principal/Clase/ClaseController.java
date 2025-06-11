package com.edutech.microservicio_principal.Clase;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clases")
public class ClaseController {

    private final ClaseService claseService;

    public ClaseController(ClaseService claseService) {
        this.claseService = claseService;
    }

    @PostMapping
    public ResponseEntity<Clase> crearClase(@Valid @RequestBody Clase clase) {
        return ResponseEntity.ok(claseService.guardarClase(clase));
    }

    @GetMapping
    public ResponseEntity<List<Clase>> obtenerClases() {
        return ResponseEntity.ok(claseService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clase> obtenerClasePorId(@PathVariable Long id) {
        return claseService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clase> actualizarClase(@PathVariable Long id, @Valid @RequestBody Clase claseActualizada) {
        return claseService.obtenerPorId(id)
                .map(claseExistente -> {
                    claseExistente.setNombre(claseActualizada.getNombre());
                    claseExistente.setDescripcion(claseActualizada.getDescripcion());
                    claseExistente.setCuposDisponibles(claseActualizada.getCuposDisponibles());
                    claseExistente.setHorario(claseActualizada.getHorario());

                    Clase claseGuardada = claseService.guardarClase(claseExistente);
                    return ResponseEntity.ok(claseGuardada);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarClase(@PathVariable Long id) {
        return claseService.eliminarClase(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}