package progra4.service;

import progra4.model.Empresa;
import progra4.repository.EmpresaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public List<Empresa> obtenerTodas() {
        return empresaRepository.findAll();
    }

    public Optional<Empresa> obtenerPorId(Long id) {
        return empresaRepository.findById(id);
    }

    public Empresa guardar(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public void eliminar(Long id) {
        empresaRepository.deleteById(id);
    }

    public Empresa buscarPorCorreo(String correo) {
        return empresaRepository.findByCorreo(correo);
    }

    public long contar() { return empresaRepository.count(); }

    public long contarPendientes() { return empresaRepository.countByAprobadaFalse(); }

    public List<Empresa> obtenerPendientes() { return empresaRepository.findByAprobadaFalse(); }

    public void aprobar(Long id) {
        Empresa e = empresaRepository.findById(id).orElse(null);
        if (e != null) {
            e.setAprobada(true);
            empresaRepository.save(e);
        }
    }
}