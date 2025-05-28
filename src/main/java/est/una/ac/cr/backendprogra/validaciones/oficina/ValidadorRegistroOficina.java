package est.una.ac.cr.backendprogra.validaciones.oficina;

import est.una.ac.cr.backendprogra.repository.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorRegistroOficina {

    @Autowired
    private RegistroRepository registroRepository;
}
