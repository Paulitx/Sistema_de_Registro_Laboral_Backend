package est.una.ac.cr.backendprogra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
/**
 * Configuration estandar que ayuda a establecer los metodos GET,PORT,PUT Y DELETE para los controllers.
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@Configuration
public class CorsConfig {

    /**
     * Define la configuración de CORS para la aplicación
     *
     * @return un objeto {@link CorsConfigurationSource} que configura las reglas de CORS
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("*")); // Permite cualquier origen
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(false); // IMPORTANTE: debe ser false si allowedOrigins es "*"

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
