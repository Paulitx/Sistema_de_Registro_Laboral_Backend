package est.una.ac.cr.backendprogra.records.registro;

import est.una.ac.cr.backendprogra.entidad.Persona;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosAgregarRegistro(@NotBlank String tipo,
                                   @NotNull LocalDateTime fechaHora,
                                   @NotNull Persona persona) {
}
