package est.una.ac.cr.backendprogra.records.oficina;

import est.una.ac.cr.backendprogra.entidad.Oficina;

public record DatosListadoOficina(Integer id, String nombre, String ubicacion, int limitePersonas, int personasActuales) {
    public DatosListadoOficina(Oficina oficina) {
        this(oficina.getId(), oficina.getNombre(), oficina.getUbicacion(), oficina.getLimitePersonas(), oficina.getPersonasActuales());
    }
}
