package est.una.ac.cr.backendprogra.records.registro;

import est.una.ac.cr.backendprogra.entidad.Persona;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarRegistro(@NotNull Integer id, String nombre, String ubicacion, int limitePersonas,
                                      Persona persona) {
}
