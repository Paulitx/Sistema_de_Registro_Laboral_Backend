package est.una.ac.cr.backendprogra.controller;


import est.una.ac.cr.backendprogra.entidad.Registro;
import est.una.ac.cr.backendprogra.repository.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * controlador que permite visualizar los reportes con estadisticas en el front, aqui se obtienen los datos necesarios
 *  * tambien lo restringe segun su rol para evitar que usuarios no autorizados entren a secciones que no deben
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@RestController
@RequestMapping("/api/reportes")
public class EstadisticaController {


    @Autowired
    private RegistroRepository registroRepository;


    /**
     * Obtiene una lista de las personas con más registros de entrada, retorna una lista de mapas con la persona y la cantidad de entradas registradas
     * Requiere que el usuario tenga rol ADMIN o VISOR
     *
     * @return ResponseEntity con la lista de personas y su conteo de entradas
     */
    @GetMapping("/topPersonas")
    @PreAuthorize("hasAnyRole('ADMIN','VISOR')")
    public ResponseEntity<List<Map<String, Object>>> obtenerTopPersonasConMasEntradas() {
        List<Object[]> resultados = registroRepository.contarEntradasPorPersona();
        List<Map<String, Object>> respuesta = resultados.stream().map(obj -> {
            Map<String, Object> map = new HashMap<>();
            map.put("persona", obj[0]);
            map.put("entradas", obj[1]);
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(respuesta);
    }


    /**
     * Obtiene una lista de las oficinas con más registros de entrada, retorna una lista de mapas con la oficina y la cantidad de entradas registradas
     * Requiere que el usuario tenga rol ADMIN o VISOR
     *
     * @return ResponseEntity con la lista de oficinas y su conteo de entradas
     */
    @GetMapping("/topOficinas")
    @PreAuthorize("hasAnyRole('ADMIN','VISOR')")
    public ResponseEntity<List<Map<String, Object>>> obtenerTopOficinasConMasEntradas() {
        List<Object[]> resultados = registroRepository.contarEntradasPorOficinaDePersona();
        List<Map<String, Object>> respuesta = resultados.stream().map(obj -> {
            Map<String, Object> map = new HashMap<>();
            map.put("oficina", obj[0]);
            map.put("entradas", obj[1]);
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(respuesta);
    }


    /**
     * Obtiene la lista de personas que actualmente se encuentran dentro de una oficina, personas con registro de entrada sin registro de salida posterior
     * Retorna una lista de mapas con el nombre de la persona
     * Requiere que el usuario tenga rol ADMIN o VISOR.
     *
     * @return ResponseEntity con la lista de personas actualmente dentro
     */
    @GetMapping("/personasDentro")
    @PreAuthorize("hasAnyRole('ADMIN','VISOR')")
    public ResponseEntity<List<Map<String, Object>>> obtenerPersonasActualmenteDentro() {
        List<String> nombres = registroRepository.personasActualmenteDentro();
        List<Map<String, Object>> respuesta = nombres.stream().map(nombre -> {
            Map<String, Object> map = new HashMap<>();
            map.put("persona", nombre);
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(respuesta);
    }

}
