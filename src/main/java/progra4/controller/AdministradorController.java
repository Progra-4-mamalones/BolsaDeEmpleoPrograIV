package progra4.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import progra4.model.Administrador;
import progra4.model.Caracteristica;
import progra4.service.AdministradorService;
import progra4.service.CaracteristicaService;
import progra4.service.EmpresaService;
import progra4.service.OferenteService;

@Controller
@RequestMapping("/admin")
public class AdministradorController {

    private final EmpresaService empresaService;
    private final OferenteService oferenteService;
    private final CaracteristicaService caracteristicaService;
    private final AdministradorService administradorService;

    public AdministradorController(
            EmpresaService empresaService,
            OferenteService oferenteService,
            CaracteristicaService caracteristicaService,
            AdministradorService administradorService) {

        this.empresaService = empresaService;
        this.oferenteService = oferenteService;
        this.caracteristicaService = caracteristicaService;
        this.administradorService = administradorService;
    }

    private boolean noEsAdmin(HttpSession session) {
        return session.getAttribute("usuarioRol") == null ||
                !session.getAttribute("usuarioRol").equals("ADMIN");
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        if (noEsAdmin(session)) {
            return "redirect:/login";
        }

        Long adminId = (Long) session.getAttribute("usuarioId");
        String adminNombre = (String) session.getAttribute("usuarioNombre");

        model.addAttribute("adminId", adminId);
        model.addAttribute("adminNombre", adminNombre);

        model.addAttribute("empresasPendientes", empresaService.contarPendientes());
        model.addAttribute("oferentesPendientes", oferenteService.contarPendientes());
        model.addAttribute("totalEmpresas", empresaService.contar());
        model.addAttribute("totalOferentes", oferenteService.contar());
        model.addAttribute("totalAdmins", administradorService.obtenerTodos().size());

        return "admin/dashboard";
    }

    @GetMapping("/aprobaciones")
    public String aprobaciones(HttpSession session, Model model) {

        if (noEsAdmin(session)) {
            return "redirect:/login";
        }

        model.addAttribute("empresas", empresaService.obtenerPendientes());
        model.addAttribute("oferentes", oferenteService.obtenerPendientes());

        return "admin/aprobaciones";
    }

    @GetMapping("/empresas")
    public String listarEmpresas(HttpSession session, Model model) {

        if (noEsAdmin(session)) {
            return "redirect:/login";
        }

        model.addAttribute("empresas", empresaService.obtenerTodas());

        return "admin/listEmpresas";
    }

    @GetMapping("/oferentes")
    public String listarOferentes(HttpSession session, Model model) {

        if (noEsAdmin(session)) {
            return "redirect:/login";
        }

        model.addAttribute("oferentes", oferenteService.obtenerTodos());

        return "admin/listOferentes";
    }

    @GetMapping("/caracteristicas")
    public String verCaracteristicas(HttpSession session, Model model) {

        if (noEsAdmin(session)) {
            return "redirect:/login";
        }

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

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("administradores", administradorService.obtenerTodos());
        return "admin/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("administrador", new Administrador());
        return "admin/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Administrador administrador) {
        administradorService.guardar(administrador);
        return "redirect:/admin";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        administradorService.eliminar(id);
        return "redirect:/admin";
    }
}