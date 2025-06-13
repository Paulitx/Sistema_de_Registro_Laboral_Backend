package est.una.ac.cr.backendprogra.config.seguridadRolers;

import est.una.ac.cr.backendprogra.entidad.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Sclase para obtener los roles de los usuarios y dar permisos.
 * Permite que el usuario tenga un rol asignado.
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
public class UsuarioDetails implements UserDetails{


    private final Usuario usuario;

    public UsuarioDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene los roles del usuario usuario
     *
     * @return una colección de objetos que representan los roles del usuario
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(usuario.getRole()));

    }
    /**
     * Obtiene la contraseña encriptada del usuario
     *
     * @return la contraseña del usuario como {@link String}.
     */
    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    /**
     * Obtiene el nombre de usuario
     *
     * @return nombre del usuario como {@link String}.
     */
    @Override
    public String getUsername() {
        return usuario.getUsername();
    }
    /**
     * Indica si la cuenta del usuario no ha expirado
     *
     * @return {@code true} si la cuenta está activa y no ha expirado, {@code false} en caso contrario
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /**
     * Indica si la cuenta del usuario no está bloqueada
     *
     * @return {@code true} si la cuenta no está bloqueada, {@code false} en caso contrario
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indica si las credenciales del usuario no han expirado
     *
     * @return {@code true} si las credenciales están vigentes, {@code false} en caso contrario
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    /**
     * Indica si el usuario está habilitado para usar el sistema.
     *
     * @return {@code true} si el usuario está habilitado, {@code false} si está deshabilitado.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}

