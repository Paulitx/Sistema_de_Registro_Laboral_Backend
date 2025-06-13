package est.una.ac.cr.backendprogra.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
/**
 * Obtiene los datos para el xml de oficina y poder mostrarlo en con el soap
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonaType", propOrder = {
        "id",
        "nombre",
        "ubicacion",
        "limitePersonas",
        "personasActuales"
})
public class OficinaType {

    private Integer id;
    private String nombre;
    private String ubicacion;
    private int limitePersonas;
    private int personasActuales;

    /**
     * Métodos getters y setters para los atributos de la clase Oficina
     * id: identificador único de la oficina
     * nombre: nombre de la oficina
     * ubicacion: ubicación física de la oficina
     * limitePersonas: capacidad máxima de personas permitidas en la oficina
     * personasActuales: cantidad actual de personas presentes en la oficina
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getLimitePersonas() {
        return limitePersonas;
    }

    public void setLimitePersonas(int limitePersonas) {
        this.limitePersonas = limitePersonas;
    }

    public int getPersonasActuales() {
        return personasActuales;
    }

    public void setPersonasActuales(int personasActuales) {
        this.personasActuales = personasActuales;
    }
}
