package est.una.ac.cr.backendprogra.records.persona;

import est.una.ac.cr.backendprogra.entidad.Oficina;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DatosActualizarPersona(@NotNull Integer id, String idUsuario, String nombre, String email,
                                     String direccion, LocalDate fechaNacimiento, String telefono, String cargo,
                                     Boolean estado, Oficina oficina) {
}
