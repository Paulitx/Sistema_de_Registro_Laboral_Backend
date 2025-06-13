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
/**
 * repositorio de registros JPA para la conexion y llamado de datos con la base de datos
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@Repository
public interface RegistroRepository extends JpaRepository<Registro, Integer> {

    /**
     * Métodos para consultar registros basados en criterios específicos:
     *
     * findTopByPersonaIdAndIdNotOrderByFechaHoraDesc: obtiene el último registro (más reciente) de una persona excluyendo un registro específico por su ID, ordenado por fecha y hora descendente
     *
     * existsByPersonaIdAndTipoAndFechaHoraAfter: verifica si existe al menos un registro para una persona con un tipo específico que ocurrió después de una fecha y hora dadas
     *
     * existsByPersonaIdAndTipoAndFechaHoraBefore: verifica si existe al menos un registro para una persona con un tipo específico que ocurrió antes de una fecha y hora dadas
     */
    Optional<Registro> findTopByPersonaIdAndIdNotOrderByFechaHoraDesc(Integer personaId, Integer excludeId);
    boolean existsByPersonaIdAndTipoAndFechaHoraAfter(Integer personaId, String tipo, LocalDateTime fechaHora);

    boolean existsByPersonaIdAndTipoAndFechaHoraBefore(Integer personaId, String tipo, LocalDateTime fechaHora);


    /**
     * Métodos para consultar registros según diferentes criterios:
     *
     * buscarPorTipoRegistro: obtiene una lista de registros cuyo tipo coincide parcial o totalmente con el valor dado
     * buscarPorFechaHoraExacta: obtiene registros cuya fecha y hora coinciden exactamente con el valor proporcionado
     * buscarPorIdPersona: obtiene todos los registros asociados a una persona específica, identificada por su ID
     */
    @Query("SELECT r FROM Registro r WHERE LOWER(r.tipo) LIKE LOWER(CONCAT('%', :tipo, '%'))")
    List<Registro> buscarPorTipoRegitro(@Param("tipo") String tipo);
    @Query("SELECT r FROM Registro r WHERE r.fechaHora = :fechaHora")
    List<Registro> buscarPorFechaHoraExacta(@Param("fechaHora") LocalDateTime fechaHora);
    @Query("SELECT r FROM Registro r WHERE r.persona.id = :personaId")
    List<Registro> buscarPorIdPersona(@Param("personaId") Integer personaId);

    /**
     * Consultas para obtener estadísticas relacionadas con registros de entradas:
     *
     * contarEntradasPorPersona: cuenta la cantidad de registros de tipo 'entrada' agrupados por el nombre de la persona ordenados de mayor a menor cantidad.
     *
     * contarEntradasPorOficinaDePersona: cuenta la cantidad de registros de tipo 'entrada' agrupados por el nombre de la oficina asociada a la persona, ordenados de mayor a menor cantidad.
     *
     * personasActualmenteDentro: obtiene los nombres de personas que están actualmente dentro
     */
    @Query("SELECT r.persona.nombre, COUNT(r) FROM Registro r WHERE r.tipo = 'entrada' GROUP BY r.persona.nombre ORDER BY COUNT(r) DESC")
    List<Object[]> contarEntradasPorPersona();

    @Query("SELECT r.persona.oficina.nombre, COUNT(r) FROM Registro r WHERE r.tipo = 'entrada' GROUP BY r.persona.oficina.nombre ORDER BY COUNT(r) DESC")
    List<Object[]> contarEntradasPorOficinaDePersona();

    @Query("SELECT r.persona.nombre FROM Registro r WHERE r.id IN (SELECT MAX(r2.id) FROM Registro r2 GROUP BY r2.persona.id) AND r.tipo = 'entrada'")
    List<String> personasActualmenteDentro();

}
