package est.una.ac.cr.backendprogra.repository;

import est.una.ac.cr.backendprogra.entidad.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {

    //filtrados
    List<Persona> findByNombreContainingIgnoreCase(String nombre);
    List<Persona> findByEmailContainingIgnoreCase(String email);
    List<Persona> findByTelefonoContainingIgnoreCase(String telefono);
    List<Persona> findByCargoContainingIgnoreCase(String cargo);
    List<Persona> findByDireccionContainingIgnoreCase(String direccion);
    List<Persona> findByFechaNacimiento(LocalDate fechaNacimiento);
    List<Persona> findByEstado(Boolean estado);
    List<Persona> findByOficinaId(Integer oficinaId);
}
