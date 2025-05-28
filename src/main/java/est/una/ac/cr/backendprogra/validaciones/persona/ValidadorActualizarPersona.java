package est.una.ac.cr.backendprogra.validaciones.persona;

import est.una.ac.cr.backendprogra.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorActualizarPersona {

    @Autowired
    private PersonaRepository personaRepository;
}
