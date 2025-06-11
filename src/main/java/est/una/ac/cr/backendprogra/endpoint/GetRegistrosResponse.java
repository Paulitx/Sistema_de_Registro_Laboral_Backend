package est.una.ac.cr.backendprogra.endpoint;

import est.una.ac.cr.backendprogra.dto.RegistroType;
import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetRegistrosResponse", propOrder = {"registros"})
@XmlRootElement(name = "getRegistrosResponse")
public class GetRegistrosResponse {

    @XmlElement(name = "registro", required = true)
    private List<RegistroType> registros = new ArrayList<RegistroType>();

    public List<RegistroType> getRegistros() {return registros;}

    public void setRegistros(List<RegistroType> registros) {this.registros = registros;}
}
