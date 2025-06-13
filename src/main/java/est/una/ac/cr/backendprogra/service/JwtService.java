package est.una.ac.cr.backendprogra.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;
/**
 * Servicio encargado de generar y validar tokens JWT
 * extraer el nombre de usuario y roles desde un token, y obtiene todos los claims
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@Service
public class JwtService {

    private final String SECRET_KEY = "ClaveSecretaMuySegura12345lldlldld";

    /**
     * Genera un token JWT para un usuario con sus roles incluidos en los claims
     *
     * @param userDetails Detalles del usuario para quien se genera el token
     * @return El token JWT como cadena String
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", roles);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
    /**
     * Extrae el nombre de usuario contenido en un token JWT
     *
     * @param token El token JWT del cual extraer el nombre de usuario
     * @return El nombre de usuario extraído del token
     */
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    /**
     * Extrae la lista de roles asignados al usuario dentro del token JWT
     *
     * @param token El token JWT del cual extraer los roles
     * @return Lista de roles extraídos del token. Si no hay roles, retorna una lista vacía
     */
    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        List<String> roles = claims.get("roles", List.class);
        return roles != null ? roles : new ArrayList<>();
    }
    /**
     * Extrae todos los claims presentes en un token JWT
     *
     * @param token El token JWT del cual extraer los claims
     * @return Los claims contenidos en el token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}