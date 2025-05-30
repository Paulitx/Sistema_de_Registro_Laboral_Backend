package est.una.ac.cr.backendprogra.records.oficina;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroOficina(@NotBlank String nombre,
                                   @NotBlank String ubicacion,
                                   @NotNull  int limitePersonas) implements DatosOficina {
}
