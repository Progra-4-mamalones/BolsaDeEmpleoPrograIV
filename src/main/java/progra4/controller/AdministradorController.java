package progra4.controller;

import progra4.model.Administrador;
import progra4.service.AdministradorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/administradores")
public class AdministradorController {

    private final AdministradorService administradorService;

    public AdministradorController(AdministradorService administradorService) {
        this.administradorService = administradorService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("administradores", administradorService.obtenerTodos());
        return "administradores/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("administrador", new Administrador());
        return "administradores/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Administrador administrador) {
        administradorService.guardar(administrador);
        return "redirect:/administradores";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        administradorService.eliminar(id);
        return "redirect:/administradores";
    }
}