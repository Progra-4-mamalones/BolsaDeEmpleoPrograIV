package progra4.service;

import progra4.model.Puesto;
import progra4.repository.PuestoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PuestoService {

    private final PuestoRepository puestoRepository;

    public PuestoService(PuestoRepository puestoRepository) {
        this.puestoRepository = puestoRepository;
    }

    public List<Puesto> obtenerTodos() {
        return puestoRepository.findAll();
    }

    public Optional<Puesto> obtenerPorId(Long id) {
        return puestoRepository.findById(id);
    }

    public Puesto guardar(Puesto puesto) {
        return puestoRepository.save(puesto);
    }

    public void eliminar(Long id) {
        puestoRepository.deleteById(id);
    }

    public List<Puesto> obtenerUltimosCincoPuestos() {
        return puestoRepository.findTop5ByTipoPublicacionAndActivoOrderByFechaDesc("PUBLICO", true);
    }

    public List<Puesto> buscarPorCaracteristicas(List<Long> ids) {
        return puestoRepository.buscarPorCaracteristicas(ids);
    }
}