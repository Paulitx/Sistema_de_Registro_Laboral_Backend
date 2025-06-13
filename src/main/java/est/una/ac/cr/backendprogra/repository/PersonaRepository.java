package est.una.ac.cr.backendprogra.repository;

import est.una.ac.cr.backendprogra.entidad.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
/**
 * repositorio de persona JPA para la conexion y llamado de datos con la base de datos
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {

    /**
     * Métodos de consulta personalizados para buscar personas según diferentes criterios:
     * buscarPorNombre: busca personas cuyo nombre contiene la cadena especificada
     * buscarPorEmail: busca personas cuyo email contiene la cadena especificada
     * buscarPorTelefono: busca personas cuyo teléfono contiene la cadena especificada
     * buscarPorCargo: busca personas cuyo cargo contiene la cadena especificada
     * buscarPorDireccion: busca personas cuya dirección contiene la cadena especificada
     * buscarPorFechaNacimiento: busca personas con la fecha de nacimiento exacta especificada
     * buscarPorEstado: busca personas según su estado
     * buscarPorOficinaId: busca personas que pertenecen a una oficina con un ID específico
     */

    @Query("SELECT p FROM Persona p WHERE (p.nombre) LIKE (CONCAT('%', :nombre, '%'))")
    List<Persona> buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT p FROM Persona p WHERE LOWER(p.email) LIKE LOWER(CONCAT('%', :email, '%'))")
    List<Persona> buscarPorEmail(@Param("email") String email);

    @Query("SELECT p FROM Persona p WHERE LOWER(p.telefono) LIKE LOWER(CONCAT('%', :telefono, '%'))")
    List<Persona> buscarPorTelefono(@Param("telefono") String telefono);

    @Query("SELECT p FROM Persona p WHERE LOWER(p.cargo) LIKE LOWER(CONCAT('%', :cargo, '%'))")
    List<Persona> buscarPorCargo(@Param("cargo") String cargo);

    @Query("SELECT p FROM Persona p WHERE LOWER(p.direccion) LIKE LOWER(CONCAT('%', :direccion, '%'))")
    List<Persona> buscarPorDireccion(@Param("direccion") String direccion);

    @Query("SELECT p FROM Persona p WHERE p.fechaNacimiento = :fechaNacimiento")
    List<Persona> buscarPorFechaNacimiento(@Param("fechaNacimiento") LocalDate fechaNacimiento);

    @Query("SELECT p FROM Persona p WHERE p.estado = :estado")
    List<Persona> buscarPorEstado(@Param("estado") Boolean estado);

    @Query("SELECT p FROM Persona p WHERE p.oficina.id = :idOficina")
    List<Persona> buscarPorOficinaId(@Param("idOficina") Integer idOficina);
}
