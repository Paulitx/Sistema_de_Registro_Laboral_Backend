package est.una.ac.cr.backendprogra.records.oficina;

/**
 * record creado par la obtencion y mapeo de datos en el actualizar de oficina
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
public record DatosActualizarOficina(String nombre,
                                     String ubicacion,
                                     int limitePersonas) implements DatosOficina {
}
