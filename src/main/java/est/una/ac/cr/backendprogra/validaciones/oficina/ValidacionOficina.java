package est.una.ac.cr.backendprogra.validaciones.oficina;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidacionOficina {

    @Autowired
    private List<IValidadorRegistroOficina> validadoresRegistros;

    @Autowired
    private List<IValidadorActualizarOficina> validadoresActualizar;


}
