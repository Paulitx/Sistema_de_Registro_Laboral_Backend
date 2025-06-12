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

@RestController
@RequestMapping("/api/reportes")
public class EstadisticaController {


    @Autowired
    private RegistroRepository registroRepository;


    ////Personas con mas ingresos
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
    ///oficinas con mas registros de entrada
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


    ///cantidad de personas dentro de una oficina
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
