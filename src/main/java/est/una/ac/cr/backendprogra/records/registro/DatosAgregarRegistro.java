package est.una.ac.cr.backendprogra.records.registro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosAgregarRegistro(@NotBlank String tipo,
                                   @NotNull LocalDateTime fechaHora,
                                   @NotNull Integer personaId) {
}
