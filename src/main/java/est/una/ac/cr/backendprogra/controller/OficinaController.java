package est.una.ac.cr.backendprogra.controller;

import est.una.ac.cr.backendprogra.entidad.Oficina;
import est.una.ac.cr.backendprogra.repository.OficinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/oficina")
public class OficinaController {

    @Autowired
    private OficinaRepository oficinaRepository;

    @GetMapping
    public List<Oficina> obtenerTodas() { return oficinaRepository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Oficina> obtenerPorId(@PathVariable Integer id){

        Optional<Oficina> oficina = oficinaRepository.findById(id);
        return oficina.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Oficina crearOficina (@RequestBody Oficina oficina) {
        return oficinaRepository.save(oficina);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Oficina> actualizarOficina (@PathVariable Integer id, @RequestBody Oficina oficinaActualizada){
        Optional <Oficina> oficina = oficinaRepository.findById(id);
        if (oficina.isPresent()) {
            Oficina oficinaCreada = oficina.get();
            oficinaCreada.setNombre(oficinaActualizada.getNombre());
            oficinaCreada.setUbicacion(oficinaActualizada.getUbicacion());
            oficinaCreada.setLimitePersonas(oficinaActualizada.getLimitePersonas());

            oficinaRepository.save(oficinaCreada);
            return ResponseEntity.ok(oficinaCreada);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOficina (@PathVariable Integer id) {
        Optional <Oficina> oficina = oficinaRepository.findById(id);
        oficina.ifPresent(value -> oficinaRepository.delete(value));
        return ResponseEntity.noContent().build();
    }

}
