package est.una.ac.cr.backendprogra.config;

import est.una.ac.cr.backendprogra.config.seguridadRolers.UsuarioDetailsService;
import est.una.ac.cr.backendprogra.security.JwtAuthenticationFilter;
import est.una.ac.cr.backendprogra.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
/**
 * Configuration que establece la seguridad del sistema y deja acceder a diferentes secciones y llamados
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtService jwtService;
    private final UsuarioDetailsService usuarioDetailsService;

    /**
     * Constructor para inyectar las dependencias necesarias para la seguridad, incluyendo el service JWT y el service de detalles de usuario
     *
     * @param jwtService Servicio para manejar tokens JWT
     * @param usuarioDetailsService Servicio para cargar detalles de usuarios
     */
    public SecurityConfig(JwtService jwtService, UsuarioDetailsService usuarioDetailsService) {
        this.jwtService = jwtService;
        this.usuarioDetailsService = usuarioDetailsService;
    }


    /**
     * Configura la cadena de filtros de seguridad http, deshabilitando CSRF, definiendo que rutas están permitidas segun roles o sin autenticación, y agrega el filtro de autenticación JWT antes del filtro de username/password
     *
     * @param http Configuración http de Spring Security
     * @return La configuración construida de la cadena de filtros de seguridad
     * @throws Exception Si ocurre un error en la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/registrador/**").hasRole("REGISTRADOR")
                        .requestMatchers("/visor/**").hasRole("VISOR")
                        .requestMatchers("/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/v3/api-docs/swagger-config",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/actuator/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtService, usuarioDetailsService), UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(usuarioDetailsService);

        return http.build();
    }

    /**
     * Configura el AuthenticationManager que se utiliza para autenticar usuarios con el servicio de detalles de usuario y el codificador de contraseñas
     *
     * @param http Configuración HTTP de Spring Security.
     * @return Un AuthenticationManager configurado.
     * @throws Exception Si ocurre un error en la configuración.
     */

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(usuarioDetailsService).passwordEncoder(passwordEncoder());
        return builder.build();
    }

    /**
     * Bean que define el PasswordEncoder a usar para codificar y verificar contraseñas, utilizando BCrypt
     *
     * @return Un codificador de contraseñas BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
