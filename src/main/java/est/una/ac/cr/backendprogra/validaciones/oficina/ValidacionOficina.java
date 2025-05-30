package est.una.ac.cr.backendprogra.validaciones.oficina;

import est.una.ac.cr.backendprogra.entidad.Oficina;
import est.una.ac.cr.backendprogra.repository.OficinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionOficina {

    @Autowired
    private OficinaRepository oficinaRepository;

    public void validar(Oficina oficina){

        if (oficina.getNombre() == null || oficina.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre no puede estar vacío");
        }

        if (oficina.getLimitePersonas() <= 0) {
            throw new RuntimeException("El límite de personas debe ser mayor a cero.");
        }
        if (oficina.getPersonasActuales() < 0) {
            throw new RuntimeException("Las personas actuales deben ser mayor o igual a cero.");
        }
        if (oficina.getPersonasActuales() > oficina.getLimitePersonas()) {
            throw new RuntimeException("No hay suficinete espacion en la oficina.");
        }
    }
}
