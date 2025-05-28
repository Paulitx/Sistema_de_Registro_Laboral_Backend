package est.una.ac.cr.backendprogra.controller;

import est.una.ac.cr.backendprogra.entidad.Registro;
import est.una.ac.cr.backendprogra.repository.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/registro")
public class RegistroController {

    @Autowired
    private RegistroRepository registroRepository;

    @GetMapping
    public List<Registro> obtenerTodos() {
        return registroRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Registro> obtenerPorId(@PathVariable Integer id) {
        Optional<Registro> registro = registroRepository.findById(id);
        return registro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Registro crearRegistro(@RequestBody Registro registro) {
        return registroRepository.save(registro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Registro> actualizarRegistro(@PathVariable Integer id, @RequestBody Registro registroActualizado) {
        Optional<Registro> registro = registroRepository.findById(id);
        if (registro.isPresent()) {
            Registro registroCreado = registro.get();
            registroCreado.setTipo(registroActualizado.getTipo());
            registroCreado.setFechaHora(registroActualizado.getFechaHora());

            registroRepository.save(registroCreado);
            return ResponseEntity.ok(registroCreado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRegistro(@PathVariable Integer id) {
        Optional<Registro> registro = registroRepository.findById(id);
        registro.ifPresent(value -> registroRepository.delete(value));
        return ResponseEntity.noContent().build();
    }
}
