package est.una.ac.cr.backendprogra.records.registro;

import java.time.LocalDateTime;

public interface DatosRegistro {
    String tipo();
    LocalDateTime fechaHora();
    Integer personaId();
}
