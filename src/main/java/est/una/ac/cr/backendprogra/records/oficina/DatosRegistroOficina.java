package est.una.ac.cr.backendprogra.records.oficina;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
/**
 * record creado par la obtencion y mapeo de datos en el registrar de oficina
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
public record DatosRegistroOficina(@NotBlank String nombre,
                                   @NotBlank String ubicacion,
                                   @NotNull  int limitePersonas) implements DatosOficina {
}
