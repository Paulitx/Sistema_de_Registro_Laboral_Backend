package est.una.ac.cr.backendprogra.repository;

import est.una.ac.cr.backendprogra.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * repositorio de usuario JPA para la conexion y llamado de datos con la base de datos
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    /**
     * Encuentra al usuairo por su nombre de usuario
     */
    Optional<Usuario> findByUsername(String username);
}
