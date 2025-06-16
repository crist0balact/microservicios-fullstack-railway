package com.edutech.microservicio_principal.Vista;

import com.edutech.microservicio_principal.Clase.ClaseService;
import com.edutech.microservicio_principal.Curso.Curso;
import com.edutech.microservicio_principal.Curso.CursoRepository;
import com.edutech.microservicio_principal.Usuario.Usuario;
import com.edutech.microservicio_principal.Usuario.UsuarioRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class VistaClasesController {

    private final ClaseService claseService;
    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;

    public VistaClasesController(ClaseService claseService,
                                 CursoRepository cursoRepository,
                                 UsuarioRepository usuarioRepository) {
        this.claseService = claseService;
        this.cursoRepository = cursoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/vista-clases/{nombreCurso}")
    public String mostrarClasesPorCurso(@PathVariable String nombreCurso,
                                        @SessionAttribute("usuarioNombre") String usuarioNombre,
                                        Model model) {

        model.addAttribute("nombreCurso", nombreCurso);
        model.addAttribute("clases", claseService.obtenerClasesPorCurso(nombreCurso));

        Curso curso = cursoRepository.findByNombre(nombreCurso).orElse(null);
        model.addAttribute("curso", curso);

        Usuario usuario = usuarioRepository.findByNombres(usuarioNombre).orElse(null);
        model.addAttribute("usuario", usuario);  // necesario para mostrar el botón correcto

        return "vista-clases";
    }
}