package est.una.ac.cr.backendprogra.endpoint;

import est.una.ac.cr.backendprogra.dto.PersonaType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase de respuesta para el soap retorna una lista de personas.
 * Esta clase contiene una lista de objetos
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetPersonasResponse", propOrder = {"personas"})
@XmlRootElement(name = "getPersonasResponse")
public class GetPersonasResponse {


    /**
     * Lista de personas que se incluye en la respuesta  soap
     *
     * La anotación @XmlElement indica que cada persona se representa como un elemento XML obligatorio llamado "persona"
     *
     * Métodos getter y setter para acceder y modificar la lista de personas
     */
    @XmlElement(name = "persona", required = true)
    private List<PersonaType> personas = new ArrayList<PersonaType>();

    public List<PersonaType> getPersonas() {
        return personas;
    }

    public void setPersonas(List<PersonaType> personas) {
        this.personas = personas;
    }
}