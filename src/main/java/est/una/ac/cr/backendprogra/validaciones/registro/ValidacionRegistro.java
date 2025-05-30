package est.una.ac.cr.backendprogra.validaciones.registro;

import est.una.ac.cr.backendprogra.entidad.Oficina;
import est.una.ac.cr.backendprogra.entidad.Registro;
import est.una.ac.cr.backendprogra.repository.OficinaRepository;
import est.una.ac.cr.backendprogra.repository.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ValidacionRegistro {
    @Autowired
    private OficinaRepository oficinaRepository;

    @Autowired
    private RegistroRepository registroRepository;


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
