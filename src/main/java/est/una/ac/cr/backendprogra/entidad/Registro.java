package est.una.ac.cr.backendprogra.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/**
 * Entidad Registro, permite la creacion de tablas en la base de datos, ademas de ser la base de informacion de Registro.
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String tipo;
    private LocalDateTime fechaHora;

    @ManyToOne
    private Persona persona;

    /**
     * Métodos getters y setters para los atributos del registro
     * Permiten acceder y modificar los siguientes datos:
     * id: Identificador único del registro
     * tipo: Tipo de registro
     * fechaHora: Fecha y hora del registro
     * persona: Persona asociada a este registro
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

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
