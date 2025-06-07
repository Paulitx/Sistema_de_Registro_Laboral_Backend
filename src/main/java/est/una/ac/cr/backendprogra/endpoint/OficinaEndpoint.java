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

@Endpoint
public class OficinaEndpoint {

    private static final String NAMESPACE_URI = "http://soapcrud.una.ac.cr/ws/oficina";

    @Autowired
    private OficinaService oficinaService;

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
