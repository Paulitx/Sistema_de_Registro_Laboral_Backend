package est.una.ac.cr.backendprogra.repository;

import est.una.ac.cr.backendprogra.entidad.Persona;
import est.una.ac.cr.backendprogra.entidad.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Integer> {

    Optional<Registro> findTopByPersonaIdAndIdNotOrderByFechaHoraDesc(Integer personaId, Integer excludeId);


    List<Registro> findByTipoContainingIgnoreCase(String tipo);
    List<Registro> findByFechaHora(LocalDateTime fechaHora);
    List<Registro> findByPersonaId(Integer personaId);
}
