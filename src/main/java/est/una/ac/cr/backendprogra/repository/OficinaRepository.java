package est.una.ac.cr.backendprogra.repository;

import est.una.ac.cr.backendprogra.entidad.Oficina;
import est.una.ac.cr.backendprogra.entidad.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OficinaRepository extends JpaRepository<Oficina, Integer> {

    List<Oficina> findByNombreContainingIgnoreCase(String nombre);
    List<Oficina> findByUbicacionContainingIgnoreCase(String ubicacion);
    List<Oficina> findByLimitePersonas(int limitePersonas);
    List<Oficina> findByPersonasActuales(int personasActuales);
}
