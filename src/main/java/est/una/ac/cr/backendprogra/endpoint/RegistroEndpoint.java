package est.una.ac.cr.backendprogra.endpoint;

import est.una.ac.cr.backendprogra.dto.RegistroType;
import est.una.ac.cr.backendprogra.entidad.Registro;
import est.una.ac.cr.backendprogra.service.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
/**
 * Endpoint que gestiona las operaciones relacionadas con registros
 * Expone el servicio "listarRegistros", que permite obtener la lista completa de registros disponibles
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@Endpoint
public class RegistroEndpoint {

    private static final String NAMESPACE_URI = "http://soapcrud.una.ac.cr/ws/registro";

    @Autowired
    private RegistroService registroService;


    /**
     * Método soap que maneja la solicitud para listar todos los registros
     *
     * @param request Objeto que representa la solicitud SOAP para obtener registros
     * @return GetRegistrosResponse Objeto que contiene la lista de registros convertidos a RegistroType para la respuesta soap
     *
     * Este método obtiene todos los registros desde el servicio, los convierte a tipos SOAP y los agrega a la respuesta.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRegistrosRequest")
    @ResponsePayload
    public GetRegistrosResponse listarRegistros(@RequestPayload GetRegistrosRequest request) {
        GetRegistrosResponse response = new GetRegistrosResponse();
        List<Registro> registros = registroService.listarRegistros();

        for (Registro registro : registros) {
            RegistroType registroType = new RegistroType();
            registroType.setId(registro.getId());
            registroType.setTipo(registro.getTipo());
            registroType.setFechaHora(registro.getFechaHora());
            response.getRegistros().add(registroType);
        }
        return response;
    }
}
