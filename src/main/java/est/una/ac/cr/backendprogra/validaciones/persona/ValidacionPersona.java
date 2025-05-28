package est.una.ac.cr.backendprogra.validaciones.persona;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidacionPersona {

    @Autowired
    private List<IValidadorRegistroPersona> validadoresRegistros;

    @Autowired
    private List<IValidadorActualizarPersona> validadoresActualizar;
}
