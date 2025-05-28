package est.una.ac.cr.backendprogra.records.registro;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosRespuestaRegistro (@NotNull Integer id, String tipo, LocalDateTime fechaHora) {
}
