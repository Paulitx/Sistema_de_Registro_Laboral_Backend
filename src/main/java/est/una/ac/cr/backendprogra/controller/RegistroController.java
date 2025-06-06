package est.una.ac.cr.backendprogra.controller;

import est.una.ac.cr.backendprogra.entidad.Persona;
import est.una.ac.cr.backendprogra.entidad.Registro;
import est.una.ac.cr.backendprogra.records.registro.DatosActualizarRegistro;
import est.una.ac.cr.backendprogra.records.registro.DatosAgregarRegistro;
import est.una.ac.cr.backendprogra.repository.RegistroRepository;
import est.una.ac.cr.backendprogra.service.RegistroService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/registro")
public class RegistroController {

    @Autowired
    private RegistroRepository registroRepository;
    @Autowired
    private RegistroService registroService;

    @GetMapping
    public ResponseEntity<List<Registro>> obtenerTodosRegistro() {
        List<Registro> registros = registroRepository.findAll();
        return ResponseEntity.ok(registros);
    }
    //paginacion
    @GetMapping("/paginacion")
    public ResponseEntity<Page<Registro>> obtenerRegistros(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaHora").descending());
        Page<Registro> registrosPaginados = registroRepository.findAll(pageable);
        return ResponseEntity.ok(registrosPaginados);
    }

    @PostMapping
    public ResponseEntity<?> crearRegistro(@Valid @RequestBody DatosAgregarRegistro registro) {
        try {
            Registro nuevoRegistro = registroService.ingresoRegitro(registro);
            return ResponseEntity.ok(nuevoRegistro);
        } catch (RuntimeException e) {
            //Esto muestra el mensaje de errror
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/{id}")
        public ResponseEntity<?> actualizarRegistro(@PathVariable Integer id, @RequestBody @Valid DatosActualizarRegistro registroExistente) {
        try {
            Registro registroActualizado = registroService.actualizarRegistro(id, registroExistente);
            return ResponseEntity.ok(registroActualizado);
        } catch (RuntimeException e) {
            //Esto muestra el mensaje de errror
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRegistro(@PathVariable Integer id) {
        try {
            registroService.eliminarRegistro(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    /////exportaciones
    @GetMapping("/exportar/excel")
    public void exportarExcel(HttpServletResponse response) throws IOException {
        registroService.exportarExcel(response);
    }

    @GetMapping("/exportar/pdf")
    public void exportarPDF(HttpServletResponse response) throws IOException {
        registroService.exportarPDF(response);
    }
    //Filtrado
    @GetMapping("/{id}")
    public ResponseEntity<Registro> obtenerPorId(@PathVariable Integer id) {
        Optional<Registro> registro = registroRepository.findById(id);
        return registro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/tipo")
    public ResponseEntity<List<Registro>> obtenerRegistroTipo(@RequestParam String tipo){
        List<Registro> listado = registroRepository.findByTipoContainingIgnoreCase(tipo);
        return ResponseEntity.ok(listado);
    }
    @GetMapping("/fechaHora")
    public ResponseEntity<List<Registro>> obtenerRegistroFechaHora(@RequestParam LocalDateTime fechaHora){
        List<Registro> listado = registroRepository.findByFechaHora(fechaHora);
        return ResponseEntity.ok(listado);
    }
    @GetMapping("/persona")
    public ResponseEntity<List<Registro>> obtenerRegistroTelefono(@RequestParam Integer personaId){
        List<Registro> listado = registroRepository.findByPersonaId(personaId);
        return ResponseEntity.ok(listado);
    }
}