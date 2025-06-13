package est.una.ac.cr.backendprogra.repository;

import est.una.ac.cr.backendprogra.entidad.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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


    @Query("SELECT r FROM Registro r WHERE LOWER(r.tipo) LIKE LOWER(CONCAT('%', :tipo, '%'))")
    List<Registro> buscarPorTipoRegitro(@Param("tipo") String tipo);
    @Query("SELECT r FROM Registro r WHERE r.fechaHora = :fechaHora")
    List<Registro> buscarPorFechaHoraExacta(@Param("fechaHora") LocalDateTime fechaHora);
    @Query("SELECT r FROM Registro r WHERE r.persona.id = :personaId")
    List<Registro> buscarPorIdPersona(@Param("personaId") Integer personaId);

    ///estadisticas, llamados a la base de datos para obtrener los datos para los graficos

    @Query("SELECT r.persona.nombre, COUNT(r) FROM Registro r WHERE r.tipo = 'entrada' GROUP BY r.persona.nombre ORDER BY COUNT(r) DESC")
    List<Object[]> contarEntradasPorPersona();

    @Query("SELECT r.persona.oficina.nombre, COUNT(r) FROM Registro r WHERE r.tipo = 'entrada' GROUP BY r.persona.oficina.nombre ORDER BY COUNT(r) DESC")
    List<Object[]> contarEntradasPorOficinaDePersona();

    @Query("SELECT r.persona.nombre FROM Registro r WHERE r.id IN (SELECT MAX(r2.id) FROM Registro r2 GROUP BY r2.persona.id) AND r.tipo = 'entrada'")
    List<String> personasActualmenteDentro();

}
