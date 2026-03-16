package progra4.service;

import progra4.model.Administrador;
import progra4.repository.AdministradorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService {

    private final AdministradorRepository administradorRepository;

    public AdministradorService(AdministradorRepository administradorRepository) {
        this.administradorRepository = administradorRepository;
    }

    public List<Administrador> obtenerTodos() {
        return administradorRepository.findAll();
    }

    public Optional<Administrador> obtenerPorId(Long id) {
        return administradorRepository.findById(id);
    }

    public Administrador guardar(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    public void eliminar(Long id) {
        administradorRepository.deleteById(id);
    }

    public Administrador buscarPorIdentificacion(String identificacion) {
        return administradorRepository.findByIdentificacion(identificacion);
    }
}