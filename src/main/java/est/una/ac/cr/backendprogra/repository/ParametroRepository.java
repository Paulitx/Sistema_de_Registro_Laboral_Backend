package est.una.ac.cr.backendprogra.repository;

import est.una.ac.cr.backendprogra.entidad.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Servicio que gestiona las operaciones relacionadas con las oficinas.
 * Permite registrar, buscar, actualizar y eliminar oficinas en el sistema.
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@Repository
public interface ParametroRepository extends JpaRepository<Parametro, Integer> {
}
