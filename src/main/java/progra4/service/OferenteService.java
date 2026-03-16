package progra4.service;

import progra4.model.Oferente;
import progra4.repository.OferenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OferenteService {

    private final OferenteRepository oferenteRepository;

    public OferenteService(OferenteRepository oferenteRepository) {
        this.oferenteRepository = oferenteRepository;
    }

    public List<Oferente> obtenerTodos() {
        return oferenteRepository.findAll();
    }

    public Optional<Oferente> obtenerPorId(Long id) {
        return oferenteRepository.findById(id);
    }

    public Oferente guardar(Oferente oferente) {
        return oferenteRepository.save(oferente);
    }

    public void eliminar(Long id) {
        oferenteRepository.deleteById(id);
    }

    public Oferente buscarPorCorreo(String correo) {
        return oferenteRepository.findByCorreo(correo);
    }
}