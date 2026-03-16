package progra4.controller;

import progra4.model.Puesto;
import progra4.service.PuestoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/puestos")
public class PuestoController {

    private final PuestoService puestoService;

    public PuestoController(PuestoService puestoService) {
        this.puestoService = puestoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("puestos", puestoService.obtenerTodos());
        return "puestos/lista";
    }

    @GetMapping("/nuevo")
    public String formulario(Model model) {
        model.addAttribute("puesto", new Puesto());
        return "puestos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Puesto puesto) {
        puestoService.guardar(puesto);
        return "redirect:/puestos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        puestoService.eliminar(id);
        return "redirect:/puestos";
    }
}