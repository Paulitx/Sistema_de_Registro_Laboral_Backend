package est.una.ac.cr.backendprogra.controller;

import est.una.ac.cr.backendprogra.entidad.Persona;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * Controlador de personas, permite todas las interacciones con el usuairo, gets, post, put y delete.
 *
 *  * tambien lo restringe segun su rol para evitar que usuarios no autorizados entren a secciones que no deben
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@RestController
@RequestMapping("/api/persona")
public class PersonaController {

    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private OficinaRepository oficinaRepository;
    @Autowired
    private PersonaService personaService;

    /**
     * Obtiene la lista completa de personas
     * Accesible para usuarios con roles ADMIN o VISOR
     *
     * @return lista de todas las personas en la base de datos
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','VISOR')")
    public ResponseEntity<List<Persona>> obtenerTodasPersonas() {
        List<Persona> personas = personaRepository.findAll();
        return ResponseEntity.ok(personas);
    }

    /**
     * Obtiene una página de personas con paginación y orden descendente por id
     * Accesible para usuarios con roles ADMIN o VISOR
     *
     * @param page número de página (0 por defecto)
     * @param size tamaño de página (5 por defecto)
     * @return ResponseEntity con la página de personas solicitada
     */
    @GetMapping("/paginado")
    @PreAuthorize("hasAnyRole('ADMIN','VISOR')")
    public ResponseEntity<Page<Persona>> personaPaginados(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Persona> personasPaginados = personaRepository.findAll(pageable);
        return ResponseEntity.ok(personasPaginados);
    }

    /**
     * Obtiene la lista completa de personas para seleccionar en registro
     * Accesible para usuarios con roles ADMIN o VISOR
     *
     * @return lista de todas las personas en la base de datos
     */
    @GetMapping("/registro")
    @PreAuthorize("hasAnyRole('ADMIN','REGISTRADOR')")
    public ResponseEntity<List<Persona>> obtenerTodasPersonasRegistrador() {
        List<Persona> personas = personaRepository.findAll();
        return ResponseEntity.ok(personas);
    }
    /**
     * Crea una nueva persona en la base de datos
     * Solo accesible para usuarios con rol ADMIN
     *
     * @param persona datos para registrar una nueva persona
     * @return ResponseEntity con la persona creada o error en caso de excepción
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
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
    /**
     * Actualiza una persona existente identificada por su id
     * Solo accesible para usuarios con rol ADMIN
     *
     * @param id identificador de la persona a actualizar
     * @param personaExistente datos para actualizar la persona
     * @return ResponseEntity con la persona actualizada o error en caso de excepción
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
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
    /**
     * Elimina una persona por su id
     * Solo accesible para usuarios con rol ADMIN
     *
     * @param id identificador de la persona a eliminar
     * @return ResponseEntity sin contenido (204) si se elimina o si no existe
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> eliminarPersona(@PathVariable Integer id){
        Optional<Persona> persona = personaRepository.findById(id);
        persona.ifPresent(value -> personaRepository.delete(value));
        return ResponseEntity.noContent().build();
    }


    /**
     * Obtiene la lista completa de personas en excel
     * Accesible para usuarios con roles ADMIN o VISOR
     *
     * @return lista de todas las personas en la base de datos
     */
    @GetMapping("/exportar/excel")
    @PreAuthorize("hasAnyRole('ADMIN','VISOR')")
    public void exportarExcel(HttpServletResponse response) throws IOException {
        personaService.exportarExcel(response);

    }
    /**
     * Obtiene la lista completa de personas en pdf
     * Accesible para usuarios con roles ADMIN o VISOR
     *
     * @return lista de todas las personas en la base de datos
     */
    @GetMapping("/exportar/pdf")
    @PreAuthorize("hasAnyRole('ADMIN','VISOR')")
    public void exportarPDF(HttpServletResponse response) throws IOException {
        personaService.exportarPDF(response);

    }


    /**
     * Obtiene la lista completa de personas dependiendo de porque se filtre, id, nombre, email, telefono, direccion, cargo, esstado, id de la oficina y fecha de nacimiento
     * Accesible para usuarios con roles ADMIN o VISOR
     *
     * @return lista de todas las oficinas en la base de datos
     */

    @GetMapping("/id/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','VISOR')")
    public ResponseEntity<Persona> obtenerPersona(@PathVariable Integer id){
        Optional<Persona> persona = personaRepository.findById(id);
        return persona.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/nombre")
    @PreAuthorize("hasAnyRole('ADMIN','VISOR')")
    public ResponseEntity<List<Persona>> obtenerPersonaNombre(@RequestParam String nombre) {
        List<Persona> listado = personaRepository.buscarPorNombre(nombre);
        return ResponseEntity.ok(listado);
    }


    @GetMapping("/email")
    @PreAuthorize("hasAnyRole('ADMIN','VISOR')")
    public ResponseEntity<List<Persona>> obtenerPersonaEmail(@RequestParam String email){
        List<Persona> listado = personaRepository.buscarPorEmail(email);
        return ResponseEntity.ok(listado);
    }
    @GetMapping("/telefono")
    @PreAuthorize("hasAnyRole('ADMIN','VISOR')")
    public ResponseEntity<List<Persona>> obtenerPersonaTelefono(@RequestParam String telefono){
        List<Persona> listado = personaRepository.buscarPorTelefono(telefono);
        return ResponseEntity.ok(listado);
    }
    @GetMapping("/direccion")
    @PreAuthorize("hasAnyRole('ADMIN','VISOR')")
    public ResponseEntity<List<Persona>> obtenerPersonaDireccion(@RequestParam String direccion){
        List<Persona> listado = personaRepository.buscarPorDireccion(direccion);
        return ResponseEntity.ok(listado);
    }
    @GetMapping("/fechaNacimiento")
    @PreAuthorize("hasAnyRole('ADMIN','VISOR')")
    public ResponseEntity<List<Persona>> obtenerPersonaFechaNacimineto(@RequestParam LocalDate fechaNacimiento){
        List<Persona> listado = personaRepository.buscarPorFechaNacimiento(fechaNacimiento);
        return ResponseEntity.ok(listado);
    }
    @GetMapping("/cargo")
    @PreAuthorize("hasAnyRole('ADMIN','VISOR')")
    public ResponseEntity<List<Persona>> obtenerPersonaCargo(@RequestParam String cargo){
        List<Persona> listado = personaRepository.buscarPorCargo(cargo);
        return ResponseEntity.ok(listado);
    }
    @GetMapping("/estado")
    @PreAuthorize("hasAnyRole('ADMIN','VISOR')")
    public ResponseEntity<List<Persona>> obtenerPersonaEstado(@RequestParam Boolean estado){
        List<Persona> listado = personaRepository.buscarPorEstado(estado);
        return ResponseEntity.ok(listado);
    }
    @GetMapping("/oficina")
    @PreAuthorize("hasAnyRole('ADMIN','VISOR')")
    public ResponseEntity<List<Persona>> obtenerPersonaOficina(@RequestParam Integer idOficina){
        List<Persona> listado = personaRepository.buscarPorOficinaId(idOficina);
        return ResponseEntity.ok(listado);
    }

}

