package est.una.ac.cr.backendprogra.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

import java.time.LocalDateTime;
/**
 * Obtiene los datos para el xml de registros y poder mostrarlo en con el soap
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegistroType", propOrder = {
        "id",
        "tipo",
        "fechaHora"
})
public class RegistroType {

    private Integer id;
    private String tipo;
    private LocalDateTime fechaHora;


    /**
     * Métodos getters y setters para los atributos de la clase Registro:
     * id: identificador único del registro
     * tipo: tipo de registro
     * fechaHora: fecha y hora en que se realizó el registro
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}
