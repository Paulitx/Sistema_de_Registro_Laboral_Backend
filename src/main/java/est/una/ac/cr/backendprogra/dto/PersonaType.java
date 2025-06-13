package est.una.ac.cr.backendprogra.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

import java.time.LocalDate;
/**
 * Obtiene los datos para el xml de personas y poder mostrarlo en con el soap
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonaType", propOrder = {
        "id",
        "idUsuario",
        "nombre",
        "email",
        "direccion",
        "fechaNacimiento",
        "telefono",
        "cargo",
        "estado"
})
public class PersonaType {

    private Integer id;
    private String idUsuario;
    private String nombre;
    private String email;
    private String direccion;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String cargo;
    private Boolean estado;


    /**
     * Métodos getters y setters para los atributos de la clase persona:
     * id: identificador único del persona
     * idUsuario: identificador personalizado o externo del persona
     * nombre: nombre completo del persona
     * email: correo electrónico del persona
     * direccion: dirección física del persona
     * fechaNacimiento: fecha de nacimiento del persona
     * telefono: número telefónico del persona
     * cargo: posición o rol del persona dentro de la organización
     * estado: estado activo o inactivo del persona
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}

