package est.una.ac.cr.backendprogra.config.seguridadRolers;

import est.una.ac.cr.backendprogra.entidad.Usuario;
import est.una.ac.cr.backendprogra.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * Servicio que verifica la existencia de un usuario.
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Obtiene un usuario por su nombre de usuario para la autenticacion
     *
     * @param username el nombre de usuario que se quiere cargar
     * @return un objeto {@link UserDetails} que representa al usuario cargado
     * @throws UsernameNotFoundException si no se encuentra un usuario con el nombre proporcionado
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        return new UsuarioDetails(usuario);
    }
}