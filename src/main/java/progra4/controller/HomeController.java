package progra4.controller;

import progra4.service.PuestoService;
import progra4.service.CaracteristicaService;
import progra4.model.Caracteristica;
import java.util.List;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final PuestoService puestoService;
    private final CaracteristicaService caracteristicaService;

    public HomeController(PuestoService puestoService, CaracteristicaService caracteristicaService) {
        this.puestoService = puestoService;
        this.caracteristicaService = caracteristicaService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("puestos", puestoService.obtenerUltimosCincoPuestos());
        return "publico/index";
    }

    @GetMapping("/puestos/buscar-por-caracteristicas")
    public String buscarPorCaracteristicas(
            @RequestParam(required = false) List<Long> caracteristicas,
            Model model) {

        List<Caracteristica> todasCaracteristicas = caracteristicaService.obtenerTodas();
        model.addAttribute("todasCaracteristicas", todasCaracteristicas);

        if (caracteristicas != null && !caracteristicas.isEmpty()) {
            // Expandir: si un id es padre, agregar los ids de sus hijos
            List<Long> idsExpandidos = new ArrayList<>(caracteristicas);
            for (Caracteristica c : todasCaracteristicas) {
                if (c.getPadre() != null && caracteristicas.contains(c.getPadre().getId())) {
                    idsExpandidos.add(c.getId());
                }
            }
            model.addAttribute("resultados", puestoService.buscarPorCaracteristicas(idsExpandidos));
            model.addAttribute("seleccionadas", caracteristicas);
        }

        return "publico/buscar-puestos";
    }
}