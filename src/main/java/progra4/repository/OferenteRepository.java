package progra4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progra4.model.Oferente;
import java.util.List;
import java.util.Optional;

public interface OferenteRepository extends JpaRepository<Oferente, Long> {

    Oferente findByCorreo(String correo);

    List<Oferente> findByAprobado(boolean aprobado);
    List<Oferente> findByAprobadoFalse();

    long countByAprobadoFalse();
    long count();

    Optional<Oferente> findById(Long id);

}