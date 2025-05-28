package est.una.ac.cr.backendprogra.records.oficina;

import jakarta.validation.constraints.NotNull;

public record DatosRespuestaOficina(@NotNull Integer id, String nombre, String ubicacion, int limitePersonas) {
}
