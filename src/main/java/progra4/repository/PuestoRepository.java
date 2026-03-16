package progra4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progra4.model.Puesto;
import progra4.model.Empresa;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PuestoRepository extends JpaRepository<Puesto, Long> {

    List<Puesto> findByEmpresa(Empresa empresa);

    List<Puesto> findByActivo(boolean activo);

    List<Puesto> findTop5ByTipoPublicacionAndActivoOrderByFechaDesc(String tipoPublicacion, Boolean activo);

    @Query("SELECT DISTINCT p FROM Puesto p JOIN p.requisitos r WHERE r.caracteristica.id IN :ids AND p.tipoPublicacion = 'PUBLICO' AND p.activo = true")
    List<Puesto> buscarPorCaracteristicas(@Param("ids") List<Long> ids);
}