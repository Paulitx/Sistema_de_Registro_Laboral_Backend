package est.una.ac.cr.backendprogra.security;

import est.una.ac.cr.backendprogra.config.seguridadRolers.UsuarioDetailsService;
import est.una.ac.cr.backendprogra.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de autenticación que intercepta cada solicitud http para validar un token JWT
 * Si el token es válido, establece la autenticación del usuario
 *
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioDetailsService usuarioDetailsService;
    public JwtAuthenticationFilter(JwtService jwtService, UsuarioDetailsService usuarioDetailsService) {
        this.jwtService = jwtService;
        this.usuarioDetailsService = usuarioDetailsService;
    }

    /**
     * Filtra cada solicitud HTTP para validar la autenticación JWT
     *
     * Obtiene el encabezado "Authorization" y verifica si contiene un token JWT con el prefijo "Bearer "
     * Si no existe o no comienza con "Bearer ", continúa el filtro sin autenticar
     * Extrae el nombre de usuario del token JWT
     * Si el usuario no está autenticado aún en el contexto de seguridad, carga los detalles del usuario
     * Crea un token de autenticación basado en el usuario y lo establece en el contexto de seguridad
     *
     * @param request   La solicitud HTTP entrante
     * @param response  La respuesta HTTP
     * @param filterChain La cadena de filtros para continuar el procesamiento
     * @throws ServletException Si ocurre un error relacionado con el servlet
     * @throws IOException      Si ocurre un error de entrada/salida
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = usuarioDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }
}