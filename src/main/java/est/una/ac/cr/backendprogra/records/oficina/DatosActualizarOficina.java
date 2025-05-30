package est.una.ac.cr.backendprogra.records.oficina;


public record DatosActualizarOficina(String nombre,
                                     String ubicacion,
                                     int limitePersonas) implements DatosOficina {
}
