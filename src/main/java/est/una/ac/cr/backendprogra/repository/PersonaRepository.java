package est.una.ac.cr.backendprogra.repository;

import est.una.ac.cr.backendprogra.entidad.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    // Búsqueda por nombre
    @Query("SELECT p FROM Persona p WHERE (p.nombre) LIKE (CONCAT('%', :nombre, '%'))")
    List<Persona> buscarPorNombre(@Param("nombre") String nombre);

    // Búsqueda por email
    @Query("SELECT p FROM Persona p WHERE LOWER(p.email) LIKE LOWER(CONCAT('%', :email, '%'))")
    List<Persona> buscarPorEmail(@Param("email") String email);

    // Búsqueda por teléfono
    @Query("SELECT p FROM Persona p WHERE LOWER(p.telefono) LIKE LOWER(CONCAT('%', :telefono, '%'))")
    List<Persona> buscarPorTelefono(@Param("telefono") String telefono);

    // Búsqueda por cargo
    @Query("SELECT p FROM Persona p WHERE LOWER(p.cargo) LIKE LOWER(CONCAT('%', :cargo, '%'))")
    List<Persona> buscarPorCargo(@Param("cargo") String cargo);

    // Búsqueda por dirección
    @Query("SELECT p FROM Persona p WHERE LOWER(p.direccion) LIKE LOWER(CONCAT('%', :direccion, '%'))")
    List<Persona> buscarPorDireccion(@Param("direccion") String direccion);

    // Búsqueda por fecha de nacimiento
    @Query("SELECT p FROM Persona p WHERE p.fechaNacimiento = :fechaNacimiento")
    List<Persona> buscarPorFechaNacimiento(@Param("fechaNacimiento") LocalDate fechaNacimiento);

    // Búsqueda por estado
    @Query("SELECT p FROM Persona p WHERE p.estado = :estado")
    List<Persona> buscarPorEstado(@Param("estado") Boolean estado);

    // Búsqueda por ID de oficina
    @Query("SELECT p FROM Persona p WHERE p.oficina.id = :idOficina")
    List<Persona> buscarPorOficinaId(@Param("idOficina") Integer idOficina);
}
