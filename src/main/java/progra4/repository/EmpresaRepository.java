package progra4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progra4.model.Empresa;
import java.util.List;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Empresa findByCorreo(String correo);

    List<Empresa> findByAprobada(boolean aprobada);

}