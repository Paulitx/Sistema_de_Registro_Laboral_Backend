package est.una.ac.cr.backendprogra.repository;

import est.una.ac.cr.backendprogra.entidad.Oficina;
import est.una.ac.cr.backendprogra.entidad.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * repositorio de oficinac JPA para la conexion y llamado de datos con la base de datos
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@Repository
public interface OficinaRepository extends JpaRepository<Oficina, Integer> {

    /**
     * Metodos de consulta personalizados para buscar oficinas según diferentes criterios:
     * buscarPorNombre: busca oficinas cuyo nombre contiene la cadena especificada
     * buscarPorUbicacion: busca oficinas cuya ubicación contiene la cadena especificada
     * buscarPorLimitePersonas: busca oficinas que tienen un límite exacto de personas
     * buscarPorPersonasActuales: busca oficinas que tienen una cantidad exacta de personas actuales
     */

    @Query("SELECT o FROM Oficina o WHERE (o.nombre) LIKE (CONCAT('%', :nombre, '%'))")
    List<Oficina> buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT o FROM Oficina o WHERE (o.ubicacion) LIKE (CONCAT('%', :ubicacion, '%'))")
    List<Oficina> buscarPorUbicacion(@Param("ubicacion") String ubicacion);

    @Query("SELECT o FROM Oficina o WHERE o.limitePersonas = :limitePersonas")
    List<Oficina> buscarPorLimitePersonas(@Param("limitePersonas") int limitePersonas);

    @Query("SELECT o FROM Oficina o WHERE o.personasActuales = :personasActuales")
    List<Oficina> buscarPorPersonasActuales(@Param("personasActuales") int personasActuales);
}
