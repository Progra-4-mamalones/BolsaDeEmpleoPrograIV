package progra4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progra4.model.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

    Administrador findByIdentificacion(String identificacion);


}