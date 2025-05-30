package est.una.ac.cr.backendprogra.validaciones.persona;

import est.una.ac.cr.backendprogra.entidad.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Component
public class ValidacionPersona {


    public void validar(Persona persona) {
        if (persona.getIdUsuario() == null || persona.getIdUsuario().isEmpty()) {
            throw new RuntimeException("El idUsuario no puede estar vacío");
        }
        if (persona.getNombre() == null || persona.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre no puede estar vacío");
        }
        if (persona.getEmail() == null || persona.getEmail().isEmpty()) {
            throw new RuntimeException("El email no puede estar vacío");
        }
        if (!persona.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new RuntimeException("El email tiene un formato inválido");
        }
        if (persona.getFechaNacimiento() == null) {
            throw new RuntimeException("La fecha de nacimiento es obligatoria");
        }
        if (persona.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new RuntimeException("La fecha de nacimiento no puede ser futura");
        }
        if (persona.getEstado() == null) {
            throw new RuntimeException("El estado no puede ser nulo");
        }
        if (persona.getOficina() == null) {
            throw new RuntimeException("La persona debe estar asignada a una oficina");
        }
    }
}
