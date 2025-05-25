package est.una.ac.cr.backendprogra.endpoint;

import est.una.ac.cr.backendprogra.dto.PersonaType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetPersonasResponse", propOrder = {"personas"})
@XmlRootElement(name = "getPersonasResponse")
public class GetPersonasResponse {

    @XmlElement(name = "persona", required = true)
    private List<PersonaType> personas = new ArrayList<PersonaType>();

    public List<PersonaType> getPersonas() {
        return personas;
    }

    public void setPersonas(List<PersonaType> personas) {
        this.personas = personas;
    }
}