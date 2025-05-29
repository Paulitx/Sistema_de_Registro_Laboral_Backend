package est.una.ac.cr.backendprogra.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
