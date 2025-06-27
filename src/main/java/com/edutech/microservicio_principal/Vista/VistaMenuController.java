package com.edutech.microservicio_principal.Vista;

import com.edutech.microservicio_principal.Clase.ClaseRepository;
import com.edutech.microservicio_principal.Curso.Curso;
import com.edutech.microservicio_principal.Curso.CursoRepository;
import com.edutech.microservicio_principal.Usuario.Usuario;
import com.edutech.microservicio_principal.Usuario.UsuarioRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@SessionAttributes("usuarioNombre")
public class VistaMenuController {

    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    public VistaMenuController(UsuarioRepository usuarioRepository,
                               CursoRepository cursoRepository,
                               ClaseRepository claseRepository) {
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
    }

    // 🔥 Nuevo mapping para responder en la raíz del dominio
    @GetMapping("/")
    public String redirigirAlMenu() {
        return "redirect:/menu";
    }

    @GetMapping("/menu")
    public String mostrarFormulario(Model model) {
        model.addAttribute("error", null);
        return "menu";
    }

    @PostMapping("/menu")
    public String procesarLogin(@RequestParam String correo,
                                 @RequestParam String contraseña,
                                 Model model) {
        Optional<Usuario> usuario = usuarioRepository.findAll().stream()
                .filter(u -> u.getCorreo().equalsIgnoreCase(correo) &&
                             u.getContraseña().equals(contraseña))
                .findFirst();

        if (usuario.isPresent()) {
            model.addAttribute("usuarioNombre", usuario.get().getNombres());
            return "bienvenida";
        } else {
            model.addAttribute("error", "Correo o contraseña inválidos");
            return "menu";
        }
    }

    @GetMapping("/bienvenida")
    public String mostrarBienvenida(@SessionAttribute("usuarioNombre") String usuarioNombre, Model model) {
        model.addAttribute("usuarioNombre", usuarioNombre);
        return "bienvenida";
    }

    @GetMapping("/vista-horario")
    public String mostrarHorario(@SessionAttribute("usuarioNombre") String usuarioNombre, Model model) {
        Usuario usuario = usuarioRepository.findByNombres(usuarioNombre).orElse(null);

        if (usuario != null) {
            model.addAttribute("cursosInscritos", usuario.getCursosInscritos());
        }

        return "vista-horario";
    }

    @GetMapping("/inscribirse-id/{id}")
    public String inscribirPorId(@PathVariable Long id,
                                 @SessionAttribute("usuarioNombre") String usuarioNombre,
                                 Model model) {
        Usuario usuario = usuarioRepository.findByNombres(usuarioNombre).orElse(null);
        Curso curso = cursoRepository.findById(id).orElse(null);

        System.out.println("Inscribiendo por ID: " + id + " → " + (curso != null ? curso.getNombre() : "Curso no encontrado"));

        if (usuario != null && curso != null && !usuario.getCursosInscritos().contains(curso)) {
            usuario.getCursosInscritos().add(curso);
            usuarioRepository.save(usuario);
        }

        model.addAttribute("usuarioNombre", usuarioNombre);
        return "redirect:/vista-horario";
    }

    @GetMapping("/cancelar-inscripcion/{id}")
    public String cancelarInscripcion(@PathVariable Long id,
                                      @SessionAttribute("usuarioNombre") String usuarioNombre,
                                      Model model) {
        Usuario usuario = usuarioRepository.findByNombres(usuarioNombre).orElse(null);
        Curso curso = cursoRepository.findById(id).orElse(null);

        if (usuario != null && curso != null) {
            usuario.getCursosInscritos().remove(curso);
            usuarioRepository.save(usuario);
        }

        model.addAttribute("usuarioNombre", usuarioNombre);
        return "redirect:/vista-horario";
    }
}