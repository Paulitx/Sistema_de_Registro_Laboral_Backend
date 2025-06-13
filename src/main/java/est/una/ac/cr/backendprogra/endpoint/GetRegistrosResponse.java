package est.una.ac.cr.backendprogra.endpoint;

import est.una.ac.cr.backendprogra.dto.RegistroType;
import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
/**
 * Clase de respuesta para el soap retorna una lista de registro.
 * Esta clase contiene una lista de objetos
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetRegistrosResponse", propOrder = {"registros"})
@XmlRootElement(name = "getRegistrosResponse")
public class GetRegistrosResponse {


    /**
     * Lista de registros que se incluye en la respuesta soap
     *
     * La anotación @XmlElement indica que cada registro se representa como un elemento XML obligatorio llamado "registro"
     *
     * Métodos getter y setter para acceder y modificar la lista de registros
     */
    @XmlElement(name = "registro", required = true)
    private List<RegistroType> registros = new ArrayList<RegistroType>();

    public List<RegistroType> getRegistros() {return registros;}

    public void setRegistros(List<RegistroType> registros) {this.registros = registros;}
}
