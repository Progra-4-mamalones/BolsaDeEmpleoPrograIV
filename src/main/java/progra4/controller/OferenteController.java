package progra4.controller;
import progra4.model.Oferente;
import progra4.service.OferenteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/oferentes")
public class OferenteController {

    private final OferenteService oferenteService;

    public OferenteController(OferenteService oferenteService) {
        this.oferenteService = oferenteService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("oferentes", oferenteService.obtenerTodos());
        return "oferentes/lista";
    }

    @GetMapping("/nuevo")
    public String formulario(Model model) {
        model.addAttribute("oferente", new Oferente());
        return "oferentes/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Oferente oferente) {
        oferenteService.guardar(oferente);
        return "redirect:/oferentes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        oferenteService.eliminar(id);
        return "redirect:/oferentes";
    }
}