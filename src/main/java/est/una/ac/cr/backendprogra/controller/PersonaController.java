package est.una.ac.cr.backendprogra.controller;

import est.una.ac.cr.backendprogra.entidad.Oficina;
import est.una.ac.cr.backendprogra.entidad.Persona;
import est.una.ac.cr.backendprogra.entidad.Registro;
import est.una.ac.cr.backendprogra.records.persona.DatosActualizarPersona;
import est.una.ac.cr.backendprogra.records.persona.DatosRegistroPersona;
import est.una.ac.cr.backendprogra.repository.OficinaRepository;
import est.una.ac.cr.backendprogra.repository.PersonaRepository;
import est.una.ac.cr.backendprogra.service.PersonaService;
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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/persona")
public class PersonaController {

    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private OficinaRepository oficinaRepository;
    @Autowired
    private PersonaService personaService;



    @GetMapping
    public ResponseEntity<List<Persona>> obtenerTodasPersonas() {
        List<Persona> personas = personaRepository.findAll();
        return ResponseEntity.ok(personas);
    }
    //paginacion
    @GetMapping("/paginado")
    public ResponseEntity<Page<Persona>> personaPaginados(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Persona> personasPaginados = personaRepository.findAll(pageable);
        return ResponseEntity.ok(personasPaginados);
    }



    @PostMapping
    public ResponseEntity<?> crearPersona(@RequestBody @Valid DatosRegistroPersona persona) {
        try {
            Persona nuevaPersona = personaService.ingresarPersona(persona);
            return ResponseEntity.ok(nuevaPersona);
        } catch (RuntimeException e) {
            //Esto muestra el mensaje de errror
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPersona(@PathVariable Integer id, @RequestBody DatosActualizarPersona personaExistente){
        try {
            Persona personaActualizada = personaService.actualizaPersona(id, personaExistente);
            return ResponseEntity.ok(personaActualizada);
        } catch (RuntimeException e) {
            //Esto muestra el mensaje de errror
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPersona(@PathVariable Integer id){
        Optional<Persona> persona = personaRepository.findById(id);
        persona.ifPresent(value -> personaRepository.delete(value));
        return ResponseEntity.noContent().build();
    }


    /////exportaciones
    @GetMapping("/exportar/excel")
    public void exportarExcel(HttpServletResponse response) throws IOException {
        personaService.exportarExcel(response);

    }

    @GetMapping("/exportar/pdf")
    public void exportarPDF(HttpServletResponse response) throws IOException {
        personaService.exportarPDF(response);

    }
    //filtrado
    @GetMapping("/{id}")
    public ResponseEntity<Persona> obtenerPersona(@PathVariable Integer id){
        Optional<Persona> persona = personaRepository.findById(id);
        return persona.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    ///filtros

    @GetMapping("/nombre")
    public ResponseEntity<List<Persona>> obtenerPersonaNombre(@RequestParam String nombre){
        List<Persona> listado = personaRepository.findByNombreContainingIgnoreCase(nombre);
        return ResponseEntity.ok(listado);
    }

    @GetMapping("/Email")
    public ResponseEntity<List<Persona>> obtenerPersonaEmail(@RequestParam String email){
        List<Persona> listado = personaRepository.findByEmailContainingIgnoreCase(email);
        return ResponseEntity.ok(listado);
    }
    @GetMapping("/telefono")
    public ResponseEntity<List<Persona>> obtenerPersonaTelefono(@RequestParam String telefono){
        List<Persona> listado = personaRepository.findByTelefonoContainingIgnoreCase(telefono);
        return ResponseEntity.ok(listado);
    }
    @GetMapping("/direccion")
    public ResponseEntity<List<Persona>> obtenerPersonaDireccion(@RequestParam String direccion){
        List<Persona> listado = personaRepository.findByDireccionContainingIgnoreCase(direccion);
        return ResponseEntity.ok(listado);
    }
    @GetMapping("/fechaNacimineto")
    public ResponseEntity<List<Persona>> obtenerPersonaFechaNacimineto(@RequestParam LocalDate fechaNacimineto){
        List<Persona> listado = personaRepository.findByFechaNacimiento(fechaNacimineto);
        return ResponseEntity.ok(listado);
    }
    @GetMapping("/cargo")
    public ResponseEntity<List<Persona>> obtenerPersonaCargo(@RequestParam String cargo){
        List<Persona> listado = personaRepository.findByCargoContainingIgnoreCase(cargo);
        return ResponseEntity.ok(listado);
    }
    @GetMapping("/estado")
    public ResponseEntity<List<Persona>> obtenerPersonaEstado(@RequestParam Boolean estado){
        List<Persona> listado = personaRepository.findByEstado(estado);
        return ResponseEntity.ok(listado);
    }
    @GetMapping("/oficina")
    public ResponseEntity<List<Persona>> obtenerPersonaOficina(@RequestParam Integer idOficina){
        List<Persona> listado = personaRepository.findByOficinaId(idOficina);
        return ResponseEntity.ok(listado);
    }

}

