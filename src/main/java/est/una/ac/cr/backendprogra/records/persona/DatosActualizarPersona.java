package est.una.ac.cr.backendprogra.records.persona;


import jakarta.validation.constraints.Email;


import java.time.LocalDate;

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
