package est.una.ac.cr.backendprogra.config;


import est.una.ac.cr.backendprogra.entidad.Usuario;
import est.una.ac.cr.backendprogra.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            if (usuarioRepository.count() == 0) {
                Usuario admin = new Usuario("admin", "1234", "ROLE_ADMIN");
                Usuario user = new Usuario("user", "1234", "ROLE_USER");
                usuarioRepository.save(admin);
                usuarioRepository.save(user);
                System.out.println("Usuarios iniciales creados");
            }
        };
    }
}