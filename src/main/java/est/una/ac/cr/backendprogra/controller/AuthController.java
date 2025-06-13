package est.una.ac.cr.backendprogra.controller;

import est.una.ac.cr.backendprogra.config.seguridadRolers.UsuarioDetails;
import est.una.ac.cr.backendprogra.entidad.Usuario;
import est.una.ac.cr.backendprogra.repository.UsuarioRepository;
import est.una.ac.cr.backendprogra.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
/**
 * Controllador que permite ka interaccion con el ingreso de los usuarios mediante las credenciales correctas
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Endpoint para autenticación de usuario, recibe un objeto Usuario con nombre de usuario y contraseña, valida las credenciales y si son correctas genera y devuelve un token JWT
     *
     * @param usuario objeto con las credenciales del usuario a autenticar
     * @return ResponseEntity con el token JWT en caso de éxito o un mensaje de error si falla la autenticación
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        Optional<Usuario> user = usuarioRepository.findByUsername(usuario.getUsername());
        if (user.isPresent() && user.get().getPassword().equals(usuario.getPassword())) {
            UserDetails userDetails = new UsuarioDetails(user.get());
            String token = jwtService.generateToken(userDetails);
            return ResponseEntity.ok(token);

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
    }
}
