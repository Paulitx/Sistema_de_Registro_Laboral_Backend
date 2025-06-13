package est.una.ac.cr.backendprogra.records.registro;


import java.time.LocalDateTime;
/**
 *  * record creado par la obtencion y mapeo de datos en el actualizar de registro
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
public record DatosActualizarRegistro(LocalDateTime fechaHora,
                                      Integer personaId){
}
