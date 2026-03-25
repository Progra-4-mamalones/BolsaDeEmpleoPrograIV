package progra4.controller;

import jakarta.servlet.http.HttpSession;
import progra4.model.Administrador;
import progra4.model.Empresa;
import progra4.model.Oferente;
import progra4.service.AdministradorService;
import progra4.service.EmpresaService;
import progra4.service.OferenteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final AdministradorService administradorService;
    private final EmpresaService empresaService;
    private final OferenteService oferenteService;

    public AuthController(AdministradorService administradorService,
                          EmpresaService empresaService,
                          OferenteService oferenteService) {
        this.administradorService = administradorService;
        this.empresaService = empresaService;
        this.oferenteService = oferenteService;
    }

    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos.");
        }
        return "auth/login";
    }
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String usuario,
                                @RequestParam String clave,
                                HttpSession session,
                                Model model) {

        // Intentar admin (usa identificacion)
        Administrador admin = administradorService.buscarPorIdentificacion(usuario);
        if (admin != null && admin.getClave().equals(clave)) {
            session.setAttribute("usuarioRol", "ADMIN");
            session.setAttribute("usuarioId", admin.getId());
            session.setAttribute("usuarioNombre", admin.getIdentificacion());
            return "redirect:/admin/dashboard";
        }

        // Intentar empresa (usa correo)
        Empresa empresa = empresaService.buscarPorCorreo(usuario);
        if (empresa != null && empresa.getClave().equals(clave)) {
            if (!empresa.isAprobada()) {
                model.addAttribute("error", "Tu cuenta aún no ha sido aprobada.");
                return "auth/login";
            }
            session.setAttribute("usuarioRol", "EMPRESA");
            session.setAttribute("usuarioId", empresa.getId());
            session.setAttribute("usuarioNombre", empresa.getNombre());
            return "redirect:/empresa/dashboard";
        }

        // Intentar oferente (usa correo)
        Oferente oferente = oferenteService.buscarPorCorreo(usuario);
        if (oferente != null && oferente.getClave().equals(clave)) {
            if (!oferente.isAprobado()) {
                model.addAttribute("error", "Tu cuenta aún no ha sido aprobada.");
                return "auth/login";
            }
            session.setAttribute("usuarioRol", "OFERENTE");
            session.setAttribute("usuarioId", oferente.getId());
            session.setAttribute("usuarioNombre", oferente.getNombre());
            return "redirect:/oferente/dashboard";
        }

        model.addAttribute("error", "Usuario o contraseña incorrectos.");
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/empresa/registro")
    public String mostrarRegistroEmpresa() {
        return "empresa/registro";
    }

    @PostMapping("/empresa/registro")
    public String procesarRegistroEmpresa(@ModelAttribute Empresa empresa, Model model) {
        if (empresaService.buscarPorCorreo(empresa.getCorreo()) != null) {
            model.addAttribute("error", "Ya existe una empresa registrada con ese correo.");
            return "empresa/registro";
        }
        empresa.setAprobada(false);
        empresa.setFechaRegistro(java.time.LocalDateTime.now());
        empresaService.guardar(empresa);
        model.addAttribute("exito", "Registro exitoso. Espere la aprobación de un administrador.");
        return "empresa/registro";
    }

    @GetMapping("/oferente/registro")
    public String mostrarRegistroOferente() {
        return "oferente/registro";
    }

    @PostMapping("/oferente/registro")
    public String procesarRegistroOferente(@ModelAttribute Oferente oferente, Model model) {
        if (oferenteService.buscarPorCorreo(oferente.getCorreo()) != null) {
            model.addAttribute("error", "Ya existe un oferente registrado con ese correo.");
            return "oferente/registro";
        }
        oferente.setAprobado(false);
        oferente.setFechaRegistro(java.time.LocalDateTime.now());
        oferenteService.guardar(oferente);
        model.addAttribute("exito", "Registro exitoso. Espere la aprobación de un administrador.");
        return "oferente/registro";
    }
}