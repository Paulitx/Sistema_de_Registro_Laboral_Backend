package est.una.ac.cr.backendprogra.records.registro;

import java.time.LocalDateTime;
/**
 * Interface para evitar redundacia de datos en los record de registro con actualziar y registrar
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
public interface DatosRegistro {
    String tipo();
    LocalDateTime fechaHora();
    Integer personaId();
}
