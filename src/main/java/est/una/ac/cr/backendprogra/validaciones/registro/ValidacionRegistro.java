package est.una.ac.cr.backendprogra.validaciones.registro;

import est.una.ac.cr.backendprogra.entidad.Oficina;
import est.una.ac.cr.backendprogra.entidad.Registro;
import est.una.ac.cr.backendprogra.repository.OficinaRepository;
import est.una.ac.cr.backendprogra.repository.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class ValidacionRegistro {
    @Autowired
    private OficinaRepository oficinaRepository;

    @Autowired
    private RegistroRepository registroRepository;


    public void validar(Integer personaId, Integer oficinaId, String tipo) {
        Oficina oficina = oficinaRepository.findById(oficinaId).orElseThrow(() -> new RuntimeException("oficina no encontrada"));
        Registro salidaFallo = registroRepository.findTopByPersonaIdOrderByFechaHoraDesc(personaId).orElse(null);

        if(Objects.equals(tipo, "Entrada")){
            if(oficina.getPersonasActuales() >= oficina.getLimitePersonas()){
                throw new RuntimeException("Límite de personas alcanzado en la oficina.");
            }
        }else if(Objects.equals(tipo, "Salida")){
            if(salidaFallo == null || "Salida".equalsIgnoreCase(salidaFallo.getTipo())){
                throw new RuntimeException("No se puede registrar una salida sin una entrada");
            }

        }



    }
}
