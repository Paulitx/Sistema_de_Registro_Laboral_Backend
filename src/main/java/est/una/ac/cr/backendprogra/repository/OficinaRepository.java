package est.una.ac.cr.backendprogra.repository;

import est.una.ac.cr.backendprogra.entidad.Oficina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OficinaRepository extends JpaRepository<Oficina, Integer> {

}
