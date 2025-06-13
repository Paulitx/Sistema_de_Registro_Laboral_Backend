package est.una.ac.cr.backendprogra.records.persona;


import jakarta.validation.constraints.Email;


import java.time.LocalDate;
/**
 * record creado par la obtencion y mapeo de datos en el actualizar de persona
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
public record DatosActualizarPersona(String idUsuario,
                                     String nombre,
                                     @Email String email,
                                     String direccion,
                                     LocalDate fechaNacimiento,
                                     String telefono,
                                     String cargo,
                                     Boolean estado,
                                     Integer oficina) implements DatosPersona {
}
