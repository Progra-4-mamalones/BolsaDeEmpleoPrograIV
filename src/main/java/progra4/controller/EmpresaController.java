package progra4.controller;

import progra4.model.Empresa;
import progra4.service.EmpresaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("empresas", empresaService.obtenerTodas());
        return "empresas/lista";
    }

    @GetMapping("/nuevo")
    public String formulario(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Empresa empresa) {
        empresaService.guardar(empresa);
        return "redirect:/empresas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        empresaService.eliminar(id);
        return "redirect:/empresas";
    }

}