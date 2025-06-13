package est.una.ac.cr.backendprogra.endpoint;

import est.una.ac.cr.backendprogra.dto.PersonaType;
import est.una.ac.cr.backendprogra.entidad.Persona;
import est.una.ac.cr.backendprogra.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


import java.util.List;
/**
 * Endpoint que gestiona las operaciones relacionadas con personas
 * Expone el servicio "listarPersonas", que permite obtener la lista completa de personas disponibles
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@Endpoint
public class PersonaEndpoint {

    private static final String NAMESPACE_URI = "http://soapcrud.una.ac.cr/ws/persona";

    @Autowired
    private PersonaService personaService;


    /**
     * Maneja la petición soap para listar todas las personas
     *
     * Recibe una solicitud de tipo GetPersonasRequest y devuelve una respuesta GetPersonasResponse que contiene una lista de personas convertidas a objetos OficinaType para el formato soap
     *
     * @param request solicitud para obtener las oficinas
     * @return respuesta con la lista de oficinas disponibles en el sistema
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPersonasRequest")
    @ResponsePayload
    public GetPersonasResponse listarPersonas(@RequestPayload GetPersonasRequest request) {
        GetPersonasResponse response = new GetPersonasResponse();
        List<Persona> personas = personaService.listarPersonas();

        for (Persona persona : personas) {
            PersonaType personaType = new PersonaType();
            personaType.setId(persona.getId());
            personaType.setIdUsuario(persona.getIdUsuario());
            personaType.setNombre(persona.getNombre());
            personaType.setEmail(persona.getEmail());
            personaType.setDireccion(persona.getDireccion());
            personaType.setFechaNacimiento(persona.getFechaNacimiento());
            personaType.setTelefono(persona.getTelefono());
            personaType.setCargo(persona.getCargo());
            personaType.setEstado(persona.getEstado());
            response.getPersonas().add(personaType);
        }

        return response;
    }
}