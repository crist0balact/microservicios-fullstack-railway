package com.edutech.microservicio_principal.Vista;

import com.edutech.microservicio_principal.Clase.ClaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class VistaClasesController {

    private final ClaseService claseService;

    public VistaClasesController(ClaseService claseService) {
        this.claseService = claseService; // Se referencia ClaseService para acceder a los datos de la base de datos
    }

    @GetMapping("/vista-clases/{nombreCurso}") // Ruta dinamica para reemplazar {nombreCurso} con el curso elegido
    public String mostrarClasesPorCurso(@PathVariable String nombreCurso, Model model) {
        model.addAttribute("nombreCurso", nombreCurso);
        model.addAttribute("clases", claseService.obtenerClasesPorCurso(nombreCurso));
        return "vista-clases"; // Busca vista-clases.html en templates (Thymeleaf)
    }
}