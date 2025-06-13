package est.una.ac.cr.backendprogra.validaciones.oficina;

import est.una.ac.cr.backendprogra.entidad.Oficina;
import est.una.ac.cr.backendprogra.repository.OficinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * Component encargado de las validaciones principales para el agregar y actualziar de oficina
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@Component
public class ValidacionOficina {

    @Autowired
    private OficinaRepository oficinaRepository;


    /**
     * Valida los datos de una oficina asegurando que cumplan con las reglas de negocio
     *
     * @param oficina La oficina a validar.
     * @throws RuntimeException si alguna validación falla, con un mensaje indicando el problema:
     *  El nombre no puede ser nulo o vacío
     *  El límite de personas debe ser mayor a cero
     *  Las personas actuales no pueden ser negativas
     *  Las personas actuales no pueden superar el límite de personas
     */
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
