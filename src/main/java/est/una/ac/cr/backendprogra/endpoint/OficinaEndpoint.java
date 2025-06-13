package est.una.ac.cr.backendprogra.endpoint;

import est.una.ac.cr.backendprogra.dto.OficinaType;
import est.una.ac.cr.backendprogra.entidad.Oficina;
import est.una.ac.cr.backendprogra.service.OficinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
/**
 * Endpoint que gestiona las operaciones relacionadas con oficinas
 * Expone el servicio "listarOficinas", que permite obtener la lista completa de oficinas disponibles
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@Endpoint
public class OficinaEndpoint {

    private static final String NAMESPACE_URI = "http://soapcrud.una.ac.cr/ws/oficina";

    @Autowired
    private OficinaService oficinaService;

    /**
     * Maneja la petición SOAP para listar todas las oficinas
     *
     * Recibe una solicitud de tipo GetOficinasRequest y devuelve una respuesta GetOficinasResponse que contiene una lista de oficinas convertidas a objetos OficinaType para el formato soap
     *
     * @param request solicitud para obtener las oficinas
     * @return respuesta con la lista de oficinas disponibles en el sistema
     */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getOficinasRequest")
    @ResponsePayload
    public GetOficinasResponse listarOficinas( @RequestPayload GetOficinasRequest request ) {
        GetOficinasResponse response = new GetOficinasResponse();
        List<Oficina> oficinas = oficinaService.listarOficinas();

        for (Oficina oficina : oficinas) {
            OficinaType oficinaType = new OficinaType();
            oficinaType.setId(oficina.getId());
            oficinaType.setNombre(oficina.getNombre());
            oficinaType.setUbicacion(oficina.getUbicacion());
            oficinaType.setLimitePersonas(oficina.getLimitePersonas());
            oficinaType.setPersonasActuales(oficina.getPersonasActuales());
            response.getOficinas().add(oficinaType);
        }
        return response;
    }
}
