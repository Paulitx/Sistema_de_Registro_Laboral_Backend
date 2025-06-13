package est.una.ac.cr.backendprogra.records.registro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
/**
 * record creado par la obtencion y mapeo de datos en el agregar de registros
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
public record DatosAgregarRegistro(@NotBlank String tipo,
                                   @NotNull LocalDateTime fechaHora,
                                   @NotNull Integer personaId)  implements DatosRegistro{
}
