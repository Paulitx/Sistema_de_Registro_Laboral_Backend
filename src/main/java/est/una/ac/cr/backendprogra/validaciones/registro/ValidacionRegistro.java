package est.una.ac.cr.backendprogra.validaciones.registro;

import est.una.ac.cr.backendprogra.entidad.Oficina;
import est.una.ac.cr.backendprogra.entidad.Registro;
import est.una.ac.cr.backendprogra.repository.OficinaRepository;
import est.una.ac.cr.backendprogra.repository.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Component encargado de las validaciones principales para el agregar y actualziar de registro
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@Component
public class ValidacionRegistro {
    @Autowired
    private OficinaRepository oficinaRepository;

    @Autowired
    private RegistroRepository registroRepository;

    /**
     * Valida un registro de entrada o salida asociado a una persona y oficina, asegurando que se cumplan las reglas de negocio para el control de acceso
     *
     * Validaciones principales:
     * Si el tipo es "Entrada":
     *   Verifica que la oficina no haya alcanzado su límite de personas actuales
     *   No permite registrar una nueva entrada si el último registro para la persona fue también una entrada sin salida intermedia
     * Si el tipo es "Salida"
     *  Verifica que exista un registro previo de entrada para la persona
     *  No permite registrar una salida si el último registro fue también una salida o no hay registro previo
     *  La hora del registro de salida debe ser posterior a la hora del último registro de entrada
     *
     * @param registro el registro a validar
     * @throws RuntimeException si alguna validación falla, con mensajes descriptivos
     *  "oficina no encontrada" si la oficina asociada no existe
     *  "Límite de personas alcanzado en la oficina." si la oficina está llena
     *  "No se puede registrar una nueva entrada sin haber salido antes." si ya hay una entrada sin salida previa
     *  "No se puede registrar una salida sin una entrada" si no hay entrada previa
     *   "El registro de salida debe de ser a una hora mayor que el registro de entrada" si la salida es anterior o igual a la entrada
     */

    public void validar(Registro registro) {
        Oficina oficina = oficinaRepository.findById(registro.getPersona().getOficina().getId()).orElseThrow(() -> new RuntimeException("oficina no encontrada"));
        Registro ultimoRegistro = registroRepository.findTopByPersonaIdAndIdNotOrderByFechaHoraDesc(registro.getPersona().getId(), registro.getId() != null ? registro.getId() : -1).orElse(null);// Si es nuevo (sin ID), ponemos -1 para que no excluya nada

        ////Validaciones para entradas
        if("Entrada".equalsIgnoreCase(registro.getTipo())) {
            if(oficina.getPersonasActuales() >= oficina.getLimitePersonas()){
                throw new RuntimeException("Límite de personas alcanzado en la oficina.");
            }
            if(ultimoRegistro != null && "Entrada".equalsIgnoreCase(ultimoRegistro.getTipo())){
                throw new RuntimeException("No se puede registrar una nueva entrada sin haber salido antes.");
            }
        }
        if("Salida".equalsIgnoreCase(registro.getTipo())) {
            if (ultimoRegistro == null || "Salida".equalsIgnoreCase(ultimoRegistro.getTipo())) {
                throw new RuntimeException("No se puede registrar una salida sin una entrada");
            }
            if(!registro.getFechaHora().isAfter(ultimoRegistro.getFechaHora())){
                throw new RuntimeException("El registro de salida debe de ser a una hora mayor que el registro de entrada");
            }
        }
    }
}
