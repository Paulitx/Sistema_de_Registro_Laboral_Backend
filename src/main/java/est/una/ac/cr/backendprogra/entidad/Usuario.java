package est.una.ac.cr.backendprogra.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * Entidad Usuario, permite la creacion de tablas en la base de datos, ademas de que se le definen las variables para que una persona pueda entrar y tenga un rol.
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@Entity
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String role;

    public Usuario() {
    }

    public Usuario(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    /**
     * Métodos getters y setters para los atributos del usuario
     * Permiten acceder y modificar los siguientes datos:
     * id: Identificador único del usuario
     * username: Nombre de usuario para login
     * password: Contraseña del usuario
     * role: Rol del usuario
     */
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
