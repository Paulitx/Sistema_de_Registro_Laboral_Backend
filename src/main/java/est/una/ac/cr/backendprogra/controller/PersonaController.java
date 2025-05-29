package est.una.ac.cr.backendprogra.controller;

import est.una.ac.cr.backendprogra.entidad.Oficina;
import est.una.ac.cr.backendprogra.entidad.Persona;
import est.una.ac.cr.backendprogra.entidad.Registro;
import est.una.ac.cr.backendprogra.records.persona.DatosRegistroPersona;
import est.una.ac.cr.backendprogra.repository.OficinaRepository;
import est.una.ac.cr.backendprogra.repository.PersonaRepository;
import est.una.ac.cr.backendprogra.service.PersonaService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
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


    //paginacion
    public ResponseEntity<Page<Persona>> personaPaginados(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Persona> personasPaginados = personaRepository.findAll(pageable);
        return ResponseEntity.ok(personasPaginados);
    }



    @PostMapping
    public ResponseEntity<Persona> crearPersona(@RequestBody DatosRegistroPersona datos) {
        Oficina oficina = oficinaRepository.findById(datos.oficina())
                .orElseThrow(() -> new RuntimeException("Oficina no encontrada"));

        Persona persona = new Persona();
        persona.setIdUsuario(datos.idUsuario());
        persona.setNombre(datos.nombre());
        persona.setEmail(datos.email());
        persona.setDireccion(datos.direccion());
        persona.setFechaNacimiento(datos.fechaNacimiento());
        persona.setTelefono(datos.telefono());
        persona.setCargo(datos.cargo());
        persona.setEstado(datos.estado());
        persona.setOficina(oficina);

        Persona personaGuardada = personaRepository.save(persona);
        return ResponseEntity.ok(personaGuardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> actualizarPersona(@PathVariable Integer id, @RequestBody Persona personaActualizada){
        Optional<Persona> persona = personaRepository.findById(id);
        if(persona.isPresent()){
            Persona personaCreada = persona.get();
            personaCreada.setIdUsuario(personaActualizada.getIdUsuario());
            personaCreada.setNombre(personaActualizada.getNombre());
            personaCreada.setEmail(personaActualizada.getEmail());
            personaCreada.setDireccion(personaActualizada.getDireccion());
            personaCreada.setFechaNacimiento(personaActualizada.getFechaNacimiento());
            personaCreada.setOficina(personaActualizada.getOficina());
            personaCreada.setTelefono(personaActualizada.getTelefono());
            personaCreada.setCargo(personaActualizada.getCargo());
            personaCreada.setEstado(personaActualizada.getEstado());

            personaRepository.save(personaCreada);
            return ResponseEntity.ok(personaCreada);
        }
        return ResponseEntity.notFound().build();
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
}

