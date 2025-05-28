package est.una.ac.cr.backendprogra.records.persona;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DatosRegistroPersona(@NotBlank String idUsuario,
                                   @NotBlank String nombre,
                                   @NotBlank @Email String email,
                                   @NotBlank String direccion,
                                   @NotNull LocalDate fechaNacimiento,
                                   @NotBlank String telefono,
                                   @NotBlank String cargo,
                                   @NotNull Boolean estado) {
}
