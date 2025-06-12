package est.una.ac.cr.backendprogra.repository;

import est.una.ac.cr.backendprogra.entidad.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Integer> {

    Optional<Registro> findTopByPersonaIdAndIdNotOrderByFechaHoraDesc(Integer personaId, Integer excludeId);
    boolean existsByPersonaIdAndTipoAndFechaHoraAfter(Integer personaId, String tipo, LocalDateTime fechaHora);

    boolean existsByPersonaIdAndTipoAndFechaHoraBefore(Integer personaId, String tipo, LocalDateTime fechaHora);


    List<Registro> findByTipoContainingIgnoreCase(String tipo);
    List<Registro> findByFechaHora(LocalDateTime fechaHora);
    List<Registro> findByPersonaId(Integer personaId);


    ///estadisticas, llamados a la base de datos para obtrener los datos para los graficos

    @Query("SELECT r.persona.nombre, COUNT(r) FROM Registro r WHERE r.tipo = 'entrada' GROUP BY r.persona.nombre ORDER BY COUNT(r) DESC")
    List<Object[]> contarEntradasPorPersona();

    @Query("SELECT r.persona.oficina.nombre, COUNT(r) FROM Registro r WHERE r.tipo = 'entrada' GROUP BY r.persona.oficina.nombre ORDER BY COUNT(r) DESC")
    List<Object[]> contarEntradasPorOficinaDePersona();

    @Query("SELECT r.persona.nombre FROM Registro r WHERE r.tipo = 'entrada' AND r.persona.id NOT IN (" + "SELECT r2.persona.id FROM Registro r2 WHERE r2.tipo = 'salida') GROUP BY r.persona.nombre")
    List<String> personasActualmenteDentro();

    @Query("SELECT r FROM Registro r")
    List<Registro> findAllRegistros();

}
