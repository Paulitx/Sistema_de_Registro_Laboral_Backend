package est.una.ac.cr.backendprogra.records.persona;

import est.una.ac.cr.backendprogra.entidad.Oficina;
import est.una.ac.cr.backendprogra.entidad.Persona;

import java.time.LocalDate;
/**
 * record creado par la obtencion y mapeo de datos en el Listado de persona
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
public record DatosListadoPersona(Integer id, String idUsuario, String nombre, String email,
                                  String direccion, LocalDate fechaNacimiento, String telefono, String cargo,
                                  Boolean estado, Oficina oficina) {
    public DatosListadoPersona (Persona persona){
        this(persona.getId(), persona.getIdUsuario(), persona.getNombre(), persona.getEmail(), persona.getDireccion(),
                persona.getFechaNacimiento(), persona.getTelefono(), persona.getCargo(), persona.getEstado(), persona.getOficina());
    }
}
