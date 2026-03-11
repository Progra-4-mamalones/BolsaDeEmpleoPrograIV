package progra4.service;

import progra4.model.Caracteristica;
import progra4.repository.CaracteristicaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CaracteristicaService {

    private final CaracteristicaRepository caracteristicaRepository;

    public CaracteristicaService(CaracteristicaRepository caracteristicaRepository) {
        this.caracteristicaRepository = caracteristicaRepository;
    }

    public List<Caracteristica> obtenerTodas() {
        return caracteristicaRepository.findAll();
    }

    public Optional<Caracteristica> obtenerPorId(Long id) {
        return caracteristicaRepository.findById(id);
    }

    public Caracteristica guardar(Caracteristica caracteristica) {
        return caracteristicaRepository.save(caracteristica);
    }

    public void eliminar(Long id) {
        caracteristicaRepository.deleteById(id);
    }
}