package com.edutech.microservicio_principal.Vista;

import com.edutech.microservicio_principal.Curso.CursoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VistaCursoController {

    private final CursoService cursoService;

    public VistaCursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping("/vista-cursos")
    public String mostrarVistaCursos(Model model) {
        model.addAttribute("cursos", cursoService.obtenerTodos());
        return "vista-cursos"; // Busca a vista-cursos.html en templates
    }
}