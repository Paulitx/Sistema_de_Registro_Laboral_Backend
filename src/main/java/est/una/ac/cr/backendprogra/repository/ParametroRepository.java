package est.una.ac.cr.backendprogra.repository;

import est.una.ac.cr.backendprogra.entidad.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametroRepository extends JpaRepository<Parametro, Integer> {
}
