package est.una.ac.cr.backendprogra.controller;

import est.una.ac.cr.backendprogra.entidad.Persona;
import est.una.ac.cr.backendprogra.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persona")
public class PersonaController {

    @Autowired
    private PersonaRepository personaRepository;

    @GetMapping
    public List<Persona> obtenerTodas(){
        return personaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> obtenerPersona(@PathVariable Integer id){
        Optional<Persona> persona = personaRepository.findById(id);
        return persona.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Persona crearPersona(@RequestBody Persona persona){
        return personaRepository.save(persona);
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

}

