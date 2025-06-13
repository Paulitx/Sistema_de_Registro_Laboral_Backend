package est.una.ac.cr.backendprogra.entidad;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
/**
 *  * Entidad Persona, permite la creacion de tablas en la base de datos, ademas de ser la base de informacion de Persona.
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String idUsuario;
    private String nombre;
    private String email;
    private String direccion;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String cargo;
    private Boolean estado;

    @ManyToOne
    private Oficina oficina;

    /**
     * Métodos getters y setters para los atributos de la entidad
     * Permiten acceder y modificar los siguientes datos:
     * oficina: Oficina asociada a la persona
     * fechaNacimiento: Fecha de nacimiento de la persona
     * direccion: Dirección de residencia
     * email: Correo electrónico
     * nombre: Nombre completo
     * idUsuario: Identificador único de usuario
     * id: Identificador interno
     * telefono: Número telefónico
     * cargo: Cargo o posición laboral
     * estado: Estado activo/inactivo de la persona
     */

    public Oficina getOficina() {
        return oficina;
    }

    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
