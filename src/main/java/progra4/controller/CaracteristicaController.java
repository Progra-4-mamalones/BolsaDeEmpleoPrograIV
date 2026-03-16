package progra4.controller;

import progra4.model.Caracteristica;
import progra4.service.CaracteristicaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/caracteristicas")
public class CaracteristicaController {

    private final CaracteristicaService caracteristicaService;

    public CaracteristicaController(CaracteristicaService caracteristicaService) {
        this.caracteristicaService = caracteristicaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("caracteristicas", caracteristicaService.obtenerTodas());
        return "caracteristicas/lista";
    }

    @GetMapping("/nuevo")
    public String formulario(Model model) {
        model.addAttribute("caracteristica", new Caracteristica());
        return "caracteristicas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Caracteristica caracteristica) {
        caracteristicaService.guardar(caracteristica);
        return "redirect:/caracteristicas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        caracteristicaService.eliminar(id);
        return "redirect:/caracteristicas";
    }
}