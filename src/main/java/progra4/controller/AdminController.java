package progra4.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import progra4.service.EmpresaService;
import progra4.service.OferenteService;
import progra4.model.Caracteristica;
import progra4.service.CaracteristicaService;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final EmpresaService empresaService;
    private final OferenteService oferenteService;
    private final CaracteristicaService caracteristicaService;

    public AdminController(EmpresaService empresaService, OferenteService oferenteService, CaracteristicaService caracteristicaService) {
        this.empresaService = empresaService;
        this.oferenteService = oferenteService;
        this.caracteristicaService = caracteristicaService;
    }

    // DASHBOARD
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        if (session.getAttribute("usuarioRol") == null ||
                !session.getAttribute("usuarioRol").equals("ADMIN")) {
            return "redirect:/login";
        }

        model.addAttribute("empresasPendientes", empresaService.contarPendientes());
        model.addAttribute("oferentesPendientes", oferenteService.contarPendientes());
        model.addAttribute("totalEmpresas", empresaService.contar());
        model.addAttribute("totalOferentes", oferenteService.contar());

        return "admin/dashboard";
    }

    // APROBACIONES
    @GetMapping("/aprobaciones")
    public String aprobaciones(HttpSession session, Model model) {

        model.addAttribute("empresas", empresaService.obtenerPendientes());
        model.addAttribute("oferentes", oferenteService.obtenerPendientes());

        return "admin/aprobaciones";
    }

    // LISTA EMPRESA
    @GetMapping("/empresas")
    public String listarEmpresas(HttpSession session, Model model) {

        model.addAttribute("empresas", empresaService.obtenerTodas());

        return "admin/listEmpresas";
    }

    // LISTA OFERENTE
    @GetMapping("/oferentes")
    public String listarOferentes(HttpSession session, Model model) {

        model.addAttribute("oferentes", oferenteService.obtenerTodos());

        return "admin/listOferentes";
    }

    // Caracteristicas
    @GetMapping("/caracteristicas")
    public String verCaracteristicas(HttpSession session, Model model) {

        model.addAttribute("caracteristicas", caracteristicaService.obtenerTodas());
        model.addAttribute("nueva", new Caracteristica());

        return "admin/caracteristicas";
    }

    @PostMapping("/empresa/aprobar/{id}")
    public String aprobarEmpresa(@PathVariable Long id) {
        empresaService.aprobar(id);
        return "redirect:/admin/aprobaciones";
    }

    @PostMapping("/empresa/rechazar/{id}")
    public String rechazarEmpresa(@PathVariable Long id) {
        empresaService.eliminar(id);
        return "redirect:/admin/aprobaciones";
    }

    @PostMapping("/oferente/aprobar/{id}")
    public String aprobarOferente(@PathVariable Long id) {
        oferenteService.aprobar(id);
        return "redirect:/admin/aprobaciones";
    }

    @PostMapping("/oferente/rechazar/{id}")
    public String rechazarOferente(@PathVariable Long id) {
        oferenteService.eliminar(id);
        return "redirect:/admin/aprobaciones";
    }

    @PostMapping("/caracteristicas")
    public String guardarCaracteristica(@ModelAttribute Caracteristica c,
                                        @RequestParam(required = false) Long padreId) {

        if (padreId != null) {
            Caracteristica padre = caracteristicaService.obtenerTodas()
                    .stream()
                    .filter(x -> x.getId().equals(padreId))
                    .findFirst()
                    .orElse(null);

            c.setPadre(padre);
        }

        caracteristicaService.guardar(c);

        return "redirect:/admin/caracteristicas";
    }
}