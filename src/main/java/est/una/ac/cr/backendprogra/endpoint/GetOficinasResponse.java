package est.una.ac.cr.backendprogra.endpoint;

import est.una.ac.cr.backendprogra.dto.OficinaType;
import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetOficinasResponse", propOrder = {"oficinas"})
@XmlRootElement(name = "getOficinasResponse")
public class GetOficinasResponse {

    @XmlElement(name = "oficina", required = true)
    private List<OficinaType> oficinas = new ArrayList<OficinaType>();

    public List<OficinaType> getOficinas() {
        return oficinas;
    }

    public void setOficinas(List<OficinaType> oficinas) {
        this.oficinas = oficinas;
    }
}
