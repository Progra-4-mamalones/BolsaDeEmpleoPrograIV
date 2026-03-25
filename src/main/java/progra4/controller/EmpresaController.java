package progra4.controller;

import progra4.config.UserDetailsImpl;
import progra4.model.Empresa;
import progra4.model.Puesto;
import progra4.model.Oferente;
import progra4.model.PuestoCaracteristica;
import progra4.model.Caracteristica;
import progra4.service.EmpresaService;
import progra4.service.PuestoService;
import progra4.service.MatchingService;
import progra4.service.CaracteristicaService;
import progra4.service.OferenteService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {

    private final EmpresaService empresaService;
    private final PuestoService puestoService;
    private final MatchingService matchingService;
    private final CaracteristicaService caracteristicaService;
    private final OferenteService oferenteService;

    public EmpresaController(EmpresaService empresaService,
                             PuestoService puestoService,
                             MatchingService matchingService,
                             CaracteristicaService caracteristicaService,
                             OferenteService oferenteService) {
        this.empresaService = empresaService;
        this.puestoService = puestoService;
        this.matchingService = matchingService;
        this.caracteristicaService = caracteristicaService;
        this.oferenteService = oferenteService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        Empresa empresa = empresaService.obtenerPorId(userDetails.getEntidadId()).orElse(null);
        model.addAttribute("empresa", empresa);
        return "empresa/dashboard";
    }

    @GetMapping("/puestos")
    public String misPuestos(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        Empresa empresa = empresaService.obtenerPorId(userDetails.getEntidadId()).orElse(null);
        if (empresa == null) return "redirect:/login";
        model.addAttribute("empresa", empresa);
        model.addAttribute("puestos", puestoService.obtenerPorEmpresa(empresa));
        return "empresa/puestos";
    }

    @GetMapping("/puestos/nuevo")
    public String crearPuestoForm(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        Empresa empresa = empresaService.obtenerPorId(userDetails.getEntidadId()).orElse(null);
        if (empresa == null) return "redirect:/login";
        model.addAttribute("empresa", empresa);
        model.addAttribute("puesto", new Puesto());
        model.addAttribute("caracteristicas", caracteristicaService.obtenerTodas());
        return "empresa/crearPuesto";
    }

    @PostMapping("/puestos")
    public String crearPuesto(@AuthenticationPrincipal UserDetailsImpl userDetails,
                              @ModelAttribute Puesto puesto,
                              @RequestParam(required = false) List<Long> caracteristicas,
                              @RequestParam(required = false) List<Integer> niveles,
                              Model model,
                              RedirectAttributes redirectAttributes) {

        Empresa empresa = empresaService.obtenerPorId(userDetails.getEntidadId()).orElse(null);
        if (empresa == null) return "redirect:/login";

        if (puesto.getTitulo() == null || puesto.getTitulo().trim().isEmpty()) {
            model.addAttribute("error", "El título del puesto es requerido");
            model.addAttribute("empresa", empresa);
            model.addAttribute("puesto", puesto);
            model.addAttribute("caracteristicas", caracteristicaService.obtenerTodas());
            return "empresa/crearPuesto";
        }

        if (puesto.getDescripcion() == null || puesto.getDescripcion().trim().isEmpty()) {
            model.addAttribute("error", "La descripción del puesto es requerida");
            model.addAttribute("empresa", empresa);
            model.addAttribute("puesto", puesto);
            model.addAttribute("caracteristicas", caracteristicaService.obtenerTodas());
            return "empresa/crearPuesto";
        }

        if (puesto.getSalario() == null || puesto.getSalario() <= 0) {
            model.addAttribute("error", "El salario debe ser mayor a 0");
            model.addAttribute("empresa", empresa);
            model.addAttribute("puesto", puesto);
            model.addAttribute("caracteristicas", caracteristicaService.obtenerTodas());
            return "empresa/crearPuesto";
        }

        List<PuestoCaracteristica> puestoCaracteristicas = new ArrayList<>();
        if (caracteristicas != null && niveles != null) {
            for (int i = 0; i < caracteristicas.size() && i < niveles.size(); i++) {
                Long cId = caracteristicas.get(i);
                Integer nivel = niveles.get(i);
                if (cId != null && cId > 0 && nivel != null && nivel >= 1 && nivel <= 5) {
                    caracteristicaService.obtenerPorId(cId).ifPresent(c -> {
                        PuestoCaracteristica pc = new PuestoCaracteristica();
                        pc.setCaracteristica(c);
                        pc.setNivel(nivel);
                        puestoCaracteristicas.add(pc);
                    });
                }
            }
        }

        if (puestoCaracteristicas.isEmpty()) {
            model.addAttribute("error", "Debes agregar al menos una característica");
            model.addAttribute("empresa", empresa);
            model.addAttribute("puesto", puesto);
            model.addAttribute("caracteristicas", caracteristicaService.obtenerTodas());
            return "empresa/crearPuesto";
        }

        puesto.setEmpresa(empresa);
        puesto.setActivo(true);
        puesto.setTipoPublicacion(puesto.getTipoPublicacion() != null ? puesto.getTipoPublicacion() : "PUBLICO");
        puestoService.guardarConCaracteristicas(puesto, puestoCaracteristicas);
        redirectAttributes.addFlashAttribute("exito", "¡Puesto creado correctamente!");
        return "redirect:/empresa/puestos";
    }

    @GetMapping("/puestos/{id}/candidatos")
    public String verCandidatos(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                @PathVariable Long id,
                                Model model) {
        Empresa empresa = empresaService.obtenerPorId(userDetails.getEntidadId()).orElse(null);
        if (empresa == null) return "redirect:/login";

        Optional<Puesto> puesto = puestoService.obtenerPorIdYEmpresa(id, empresa);
        if (puesto.isEmpty()) return "redirect:/empresa/puestos";

        model.addAttribute("empresa", empresa);
        model.addAttribute("puesto", puesto.get());
        model.addAttribute("candidatos", matchingService.obtenerCandidatos(puesto.get()));
        model.addAttribute("matchingService", matchingService);
        return "empresa/candidatos";
    }

    @GetMapping("/candidatos/{id}")
    public String verDetalleCandidato(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @PathVariable Long id,
                                      Model model) {
        Empresa empresa = empresaService.obtenerPorId(userDetails.getEntidadId()).orElse(null);
        if (empresa == null) return "redirect:/login";

        Optional<Oferente> oferente = oferenteService.obtenerPorId(id);
        if (oferente.isEmpty()) return "redirect:/empresa/dashboard";

        model.addAttribute("empresa", empresa);
        model.addAttribute("candidato", oferente.get());
        return "empresa/detalleCandidato";
    }

    @PostMapping("/puestos/{id}/cambiar-estado")
    public String cambiarEstadoPuesto(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @PathVariable Long id,
                                      @RequestParam boolean activo,
                                      RedirectAttributes redirectAttributes) {
        Empresa empresa = empresaService.obtenerPorId(userDetails.getEntidadId()).orElse(null);
        if (empresa == null) return "redirect:/login";

        puestoService.obtenerPorIdYEmpresa(id, empresa).ifPresent(p -> {
            puestoService.activarDesactivar(id, activo);
            String msg = activo ? "¡Puesto activado correctamente!" : "¡Puesto desactivado correctamente!";
            redirectAttributes.addFlashAttribute("exito", msg);
        });

        return "redirect:/empresa/puestos";
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