package est.una.ac.cr.backendprogra.records.oficina;
/**
 * Interface para evitar redundacia de datos en los record de oficina con actualziar y registrar
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
public interface DatosOficina {
    String nombre();
    String ubicacion();
    int limitePersonas();
}
