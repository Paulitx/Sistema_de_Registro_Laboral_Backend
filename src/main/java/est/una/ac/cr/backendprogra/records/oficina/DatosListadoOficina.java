package est.una.ac.cr.backendprogra.records.oficina;

import est.una.ac.cr.backendprogra.entidad.Oficina;
/**
 * record creado par la obtencion y mapeo de datos en el Listado de oficina
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
public record DatosListadoOficina(Integer id, String nombre, String ubicacion, int limitePersonas, int personasActuales) {
    public DatosListadoOficina(Oficina oficina) {
        this(oficina.getId(), oficina.getNombre(), oficina.getUbicacion(), oficina.getLimitePersonas(), oficina.getPersonasActuales());
    }
}
