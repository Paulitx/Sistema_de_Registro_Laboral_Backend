package est.una.ac.cr.backendprogra.validaciones.registro;

import est.una.ac.cr.backendprogra.repository.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidadorActualizarRegistro {

    @Autowired
    private RegistroRepository registroRepository;
}
