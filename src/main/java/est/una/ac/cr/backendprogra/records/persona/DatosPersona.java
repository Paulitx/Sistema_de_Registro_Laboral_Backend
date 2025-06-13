package est.una.ac.cr.backendprogra.records.persona;

import java.time.LocalDate;
/**
 * Interface para evitar redundacia de datos en los record de oficina con actualziar y registrar
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
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
