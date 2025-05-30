package est.una.ac.cr.backendprogra.records.persona;

import java.time.LocalDate;

public interface DatosPersona {
    String idUsuario();
    String nombre();
    String email();
    String direccion();
    LocalDate fechaNacimiento();
    String telefono();
    String cargo();
    Boolean estado();
    Integer oficina();
}
