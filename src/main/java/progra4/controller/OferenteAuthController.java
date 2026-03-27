package progra4.controller;

import progra4.model.Caracteristica;
import progra4.model.Oferente;
import progra4.model.OferenteCaracteristica;
import progra4.model.Curriculum;
import progra4.service.CaracteristicaService;
import progra4.service.CurriculumService;
import progra4.service.OferenteCaracteristicaService;
import progra4.service.OferenteService;
import progra4.config.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import java.nio.file.*;

@Controller
@RequestMapping("/oferente")
public class OferenteAuthController {

    private final OferenteService oferenteService;
    private final CaracteristicaService caracteristicaService;
    private final OferenteCaracteristicaService ocService;
    private final CurriculumService curriculumService;

    public OferenteAuthController(OferenteService oferenteService,
                                  CaracteristicaService caracteristicaService,
                                  OferenteCaracteristicaService ocService,
                                  CurriculumService curriculumService) {
        this.oferenteService = oferenteService;
        this.caracteristicaService = caracteristicaService;
        this.ocService = ocService;
        this.curriculumService = curriculumService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        Oferente oferente = oferenteService.obtenerPorId(userDetails.getEntidadId()).orElse(null);
        model.addAttribute("oferente", oferente);
        return "oferente/dashboard";
    }

    @GetMapping("/habilidades")
    public String habilidades(@RequestParam(required = false) Long actualId,
                              @AuthenticationPrincipal UserDetailsImpl userDetails,
                              Model model) {
        Long oferenteId = userDetails.getEntidadId();
        Oferente oferente = oferenteService.obtenerPorId(oferenteId).orElse(null);

        model.addAttribute("habilidades", ocService.obtenerPorOferente(oferenteId));

        List<Caracteristica> todasCaracteristicas = caracteristicaService.obtenerTodas();
        model.addAttribute("todasCaracteristicas", todasCaracteristicas);

        if (actualId != null) {
            Caracteristica actual = todasCaracteristicas.stream()
                    .filter(c -> c.getId().equals(actualId))
                    .findFirst().orElse(null);
            model.addAttribute("actual", actual);

            List<Caracteristica> subs = todasCaracteristicas.stream()
                    .filter(c -> c.getPadre() != null && c.getPadre().getId().equals(actualId))
                    .toList();
            model.addAttribute("subcategorias", subs);

            List<Caracteristica> ruta = new ArrayList<>();
            Caracteristica nodo = actual;
            while (nodo != null) {
                ruta.add(0, nodo);
                nodo = nodo.getPadre();
            }
            model.addAttribute("ruta", ruta);
        } else {
            List<Caracteristica> raices = todasCaracteristicas.stream()
                    .filter(c -> c.getPadre() == null)
                    .toList();
            model.addAttribute("subcategorias", raices);
            model.addAttribute("ruta", List.of());
        }

        model.addAttribute("oferente", oferente);
        return "oferente/habilidades";
    }

    @PostMapping("/habilidades/agregar")
    public String agregarHabilidad(@RequestParam Long caracteristicaId,
                                   @RequestParam Integer nivel,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long oferenteId = userDetails.getEntidadId();

        // Verificar si ya existe
        boolean yaExiste = ocService.obtenerPorOferente(oferenteId).stream()
                .anyMatch(oc -> oc.getCaracteristica().getId().equals(caracteristicaId));

        if (yaExiste) {
            return "redirect:/oferente/habilidades?actualId=" + caracteristicaId + "&error=true";
        }

        OferenteCaracteristica oc = new OferenteCaracteristica();
        oc.setOferente(oferenteService.obtenerPorId(oferenteId).orElseThrow());
        oc.setCaracteristica(caracteristicaService.obtenerPorId(caracteristicaId).orElseThrow());
        oc.setNivel(nivel);
        ocService.guardar(oc);

        return "redirect:/oferente/habilidades?actualId=" + caracteristicaId + "&exito=true";
    }

    @GetMapping("/curriculum")
    public String curriculum(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        Long id = userDetails.getEntidadId();
        Oferente oferente = oferenteService.obtenerPorId(id).orElse(null);
        model.addAttribute("oferente", oferente);
        model.addAttribute("curriculum", curriculumService.obtenerPorOferente(id).orElse(null));
        return "oferente/curriculum";
    }

    @PostMapping("/curriculum/subir")
    public String subirCurriculum(@RequestParam("archivo") MultipartFile archivo,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails,
                                  Model model) {
        Long id = userDetails.getEntidadId();
        Oferente oferente = oferenteService.obtenerPorId(id).orElseThrow();
        try {
            curriculumService.guardar(oferente, archivo);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al subir el archivo: " + e.getMessage());
            model.addAttribute("oferente", oferente);
            model.addAttribute("curriculum", curriculumService.obtenerPorOferente(id).orElse(null));
            return "oferente/curriculum";
        }
        return "redirect:/oferente/curriculum";
    }

    @GetMapping("/curriculum/ver")
    public String verCurriculum(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long id = userDetails.getEntidadId();
        Optional<Curriculum> curriculum = curriculumService.obtenerPorOferente(id);
        if (curriculum.isEmpty()) return "redirect:/oferente/curriculum";
        return "redirect:/uploads/curriculum/" + curriculum.get().getArchivo();
    }

    @GetMapping("/curriculum/descargar/{nombre}")
    public ResponseEntity<Resource> descargarCurriculum(@PathVariable String nombre) throws Exception {

        Path ruta = Paths.get("C:/uploads/curriculum").resolve(nombre);
        Resource recurso = new UrlResource(ruta.toUri());

        if (!recurso.exists() || !recurso.isReadable()) {
            throw new RuntimeException("Archivo no encontrado: " + nombre);
        }

        String tipo = Files.probeContentType(ruta);
        if (tipo == null) {
            tipo = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(tipo))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);
    }
}