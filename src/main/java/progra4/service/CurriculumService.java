package progra4.service;

import progra4.model.Curriculum;
import progra4.repository.CurriculumRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurriculumService {

    private final CurriculumRepository curriculumRepository;

    public CurriculumService(CurriculumRepository curriculumRepository) {
        this.curriculumRepository = curriculumRepository;
    }

    public List<Curriculum> obtenerTodos() {
        return curriculumRepository.findAll();
    }

    public Optional<Curriculum> obtenerPorId(Long id) {
        return curriculumRepository.findById(id);
    }

    public Curriculum guardar(Curriculum curriculum) {
        return curriculumRepository.save(curriculum);
    }

    public void eliminar(Long id) {
        curriculumRepository.deleteById(id);
    }
}