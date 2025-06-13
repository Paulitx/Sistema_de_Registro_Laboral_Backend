package est.una.ac.cr.backendprogra.endpoint;

import est.una.ac.cr.backendprogra.dto.OficinaType;
import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
/**
* Clase de respuesta para el soap retorna una lista de oficinas.
 * Esta clase contiene una lista de objetos
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetOficinasResponse", propOrder = {"oficinas"})
@XmlRootElement(name = "getOficinasResponse")
public class GetOficinasResponse {


    /**
     * Lista de oficinas que se incluye en la respuesta soap
     *
     * Métodos getter y setter para acceder y modificar la lista de oficinas
     * La anotación @XmlElement indica que cada oficina se representa como un elemento XML obligatorio
     */

    @XmlElement(name = "oficina", required = true)
    private List<OficinaType> oficinas = new ArrayList<OficinaType>();

    public List<OficinaType> getOficinas() {
        return oficinas;
    }

    public void setOficinas(List<OficinaType> oficinas) {
        this.oficinas = oficinas;
    }
}
