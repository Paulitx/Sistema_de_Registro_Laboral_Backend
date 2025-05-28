package est.una.ac.cr.backendprogra.records.registro;

import est.una.ac.cr.backendprogra.entidad.Persona;
import est.una.ac.cr.backendprogra.entidad.Registro;

import java.time.LocalDateTime;

public record DatosListadoRegistro(Integer id, String tipo, LocalDateTime fechaHora, Persona persona) {
    public DatosListadoRegistro(Registro registro) {
        this(registro.getId(), registro.getTipo(), registro.getFechaHora(), registro.getPersona());
    }
}
