package progra4.controller;

import progra4.model.Curriculum;
import progra4.service.CurriculumService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/curriculums")
public class CurriculumController {

    private final CurriculumService curriculumService;

    public CurriculumController(CurriculumService curriculumService) {
        this.curriculumService = curriculumService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("curriculums", curriculumService.obtenerTodos());
        return "curriculums/lista";
    }

    @GetMapping("/nuevo")
    public String formulario(Model model) {
        model.addAttribute("curriculum", new Curriculum());
        return "curriculums/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Curriculum curriculum) {
        curriculumService.guardar(curriculum);
        return "redirect:/curriculums";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        curriculumService.eliminar(id);
        return "redirect:/curriculums";
    }
}