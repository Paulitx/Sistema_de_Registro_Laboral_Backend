package est.una.ac.cr.backendprogra.config;


import est.una.ac.cr.backendprogra.entidad.Usuario;
import est.una.ac.cr.backendprogra.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * Configuration que establece los roles de los usuarios de manera previa para los accesos y permisos.
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@Configuration
public class DataInitializer {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Bean que se ejecuta al iniciar la aplicación para poblar la base de datos con usuarios predeterminados si no hay ninguno registrado
     *
     * @return CommandLineRunner que realiza la inicialización.
     */
    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            if (usuarioRepository.count() == 0) {
                Usuario admin = new Usuario("admin", "1234", "ROLE_ADMIN");
                Usuario visor = new Usuario("visor",  "0987", "ROLE_VISOR");
                Usuario registrador = new Usuario("registrador", "5678", "ROLE_REGISTRADOR");

                usuarioRepository.save(admin);
                usuarioRepository.save(visor);
                usuarioRepository.save(registrador);
                System.out.println("Usuarios iniciales creados");

            }
        };
    }
}