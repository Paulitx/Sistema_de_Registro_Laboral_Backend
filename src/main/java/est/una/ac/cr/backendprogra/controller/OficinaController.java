package est.una.ac.cr.backendprogra.controller;

import est.una.ac.cr.backendprogra.entidad.Oficina;
import est.una.ac.cr.backendprogra.records.oficina.DatosActualizarOficina;
import est.una.ac.cr.backendprogra.records.oficina.DatosRegistroOficina;
import est.una.ac.cr.backendprogra.repository.OficinaRepository;
import est.una.ac.cr.backendprogra.service.OficinaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/oficina")
public class OficinaController {

    @Autowired
    private OficinaRepository oficinaRepository;
    @Autowired
    private OficinaService oficinaService;

    @GetMapping
    public List<Oficina> obtenerTodas() { return oficinaRepository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Oficina> obtenerPorId(@PathVariable Integer id){

        Optional<Oficina> oficina = oficinaRepository.findById(id);
        return oficina.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crearOficina (@RequestBody @Valid DatosRegistroOficina oficina) {
        try {
            Oficina nuevaOficina = oficinaService.ingresoOficina(oficina);
            return ResponseEntity.ok(nuevaOficina);
        } catch (RuntimeException e) {
            //Esto muestra el mensaje de errror
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarOficina (@PathVariable Integer id, @RequestBody DatosActualizarOficina oficina){
        try {
            Oficina oficinaActualizada = oficinaService.actualizaOficina(id, oficina);
            return ResponseEntity.ok(oficinaActualizada);
        } catch (RuntimeException e) {
            //Esto muestra el mensaje de errror
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOficina (@PathVariable Integer id) {
        Optional <Oficina> oficina = oficinaRepository.findById(id);
        oficina.ifPresent(value -> oficinaRepository.delete(value));
        return ResponseEntity.noContent().build();
    }

    ///filtrado
    @GetMapping("/nombre")
    public ResponseEntity<List<Oficina>> obtenerOficinaNombre(@RequestParam String nombre){
        List<Oficina> listado = oficinaRepository.findByNombreContainingIgnoreCase(nombre);
        return ResponseEntity.ok(listado);
    }
    @GetMapping("/Email")
    public ResponseEntity<List<Oficina>> obtenerOficinaUbicacion(@RequestParam String ubicacion){
        List<Oficina> listado = oficinaRepository.findByUbicacionContainingIgnoreCase(ubicacion);
        return ResponseEntity.ok(listado);
    }
    @GetMapping("/telefono")
    public ResponseEntity<List<Oficina>> obtenerOficinaLimitePersonas(@RequestParam int limitePersonas){
        List<Oficina> listado = oficinaRepository.findByLimitePersonas(limitePersonas);
        return ResponseEntity.ok(listado);
    }
    @GetMapping("/direccion")
    public ResponseEntity<List<Oficina>> obtenerOficinaPersonasActuales(@RequestParam int personasActuales){
        List<Oficina> listado = oficinaRepository.findByPersonasActuales(personasActuales);
        return ResponseEntity.ok(listado);
    }
}
