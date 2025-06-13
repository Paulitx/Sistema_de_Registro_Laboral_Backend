package est.una.ac.cr.backendprogra.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * Entidad oficina, permite la creacion de tablas en la base de datos, ademas de ser la base de informacion de oficinas
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Oficina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String ubicacion;
    private int limitePersonas;
    private int personasActuales;

    public int getPersonasActuales() {
        return personasActuales;
    }

    public void setPersonasActuales(int personasActuales) {
        this.personasActuales = personasActuales;
    }

    public Integer getId() {
        return id;
    }
    /**
     * Métodos getters y setters para los atributos de la entidad Oficina
     * Permiten acceder y modificar los valores de:
     * id: Identificador único de la oficina
     * nombre: Nombre de la oficina
     * ubicacion: Ubicación física de la oficina
     * limitePersonas: Capacidad máxima de personas permitidas en la oficina
     * personasActuales: Número actual de personas presentes en la oficina
     */
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
}
