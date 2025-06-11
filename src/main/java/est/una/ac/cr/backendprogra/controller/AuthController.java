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

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
