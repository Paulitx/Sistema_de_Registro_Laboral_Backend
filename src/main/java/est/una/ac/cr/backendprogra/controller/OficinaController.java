package est.una.ac.cr.backendprogra.controller;

import est.una.ac.cr.backendprogra.entidad.Oficina;
import est.una.ac.cr.backendprogra.entidad.Persona;
import est.una.ac.cr.backendprogra.records.oficina.DatosActualizarOficina;
import est.una.ac.cr.backendprogra.records.oficina.DatosRegistroOficina;
import est.una.ac.cr.backendprogra.repository.OficinaRepository;
import est.una.ac.cr.backendprogra.service.OficinaService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * Controlador de oficina, permite todas las interacciones con el usuairo, gets, post, put y delete.
 *  * tambien lo restringe segun su rol para evitar que usuarios no autorizados entren a secciones que no deben
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@RestController
@RequestMapping("/api/oficina")
public class OficinaController {

    @Autowired
    private OficinaRepository oficinaRepository;
    @Autowired
    private OficinaService oficinaService;


    /**
     * Obtiene la lista completa de oficinas
     * Accesible para usuarios con roles ADMIN o VISOR
     *
     * @return lista de todas las oficinas en la base de datos
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','VISOR')")
    public List<Oficina> obtenerTodas() { return oficinaRepository.findAll(); }

    /**
     * Obtiene una página de oficinas con paginación y orden descendente por id
     * Accesible para usuarios con roles ADMIN o VISOR
     *
     * @param page número de página (0 por defecto)
     * @param size tamaño de página (5 por defecto)
     * @return ResponseEntity con la página de oficinas solicitada
     */
    @GetMapping("/paginado")
    @PreAuthorize("hasAnyRole('ADMIN','VISOR')")
    public ResponseEntity<Page<Oficina>> oficinasPaginadas(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Oficina> oficinasPaginadas = oficinaRepository.findAll(pageable);
        return ResponseEntity.ok(oficinasPaginadas);
    }

    /**
     * Crea una nueva oficina en la base de datos
     * Solo accesible para usuarios con rol ADMIN
     *
     * @param oficina datos para registrar una nueva oficina (validado)
     * @return ResponseEntity con la oficina creada o error en caso de excepción
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
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
    /**
     * Actualiza una oficina existente identificada por su id
     * Solo accesible para usuarios con rol ADMIN
     *
     * @param id identificador de la oficina a actualizar
     * @param oficina datos para actualizar la oficina
     * @return ResponseEntity con la oficina actualizada o error en caso de excepción
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
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
    /**
     * Elimina una oficina por su id
     * Solo accesible para usuarios con rol ADMIN
     *
     * @param id identificador de la oficina a eliminar
     * @return ResponseEntity sin contenido (204) si se elimina o si no existe
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> eliminarOficina (@PathVariable Integer id) {
        Optional <Oficina> oficina = oficinaRepository.findById(id);
        oficina.ifPresent(value -> oficinaRepository.delete(value));
        return ResponseEntity.noContent().build();
    }



    /**
     * Obtiene la lista completa de oficinas en excel
     * Accesible para usuarios con roles ADMIN o VISOR
     *
     * @return lista de todas las oficinas en la base de datos
     */
    @GetMapping("/exportar/excel")
    @PreAuthorize("hasAnyRole('ADMIN', 'VISOR')")
    public void exportarExcel(HttpServletResponse response) throws IOException {
        oficinaService.exportarExcel(response);
    }
    /**
     * Obtiene la lista completa de oficinas en pdf
     * Accesible para usuarios con roles ADMIN o VISOR
     *
     * @return lista de todas las oficinas en la base de datos
     */
    @GetMapping("/exportar/pdf")
    @PreAuthorize("hasAnyRole('ADMIN', 'VISOR')")
    public void exportarPDF(HttpServletResponse response) throws IOException {
        oficinaService.exportarPDF(response);
    }



    /**
     * Obtiene la lista completa de oficinas dependiendo de porque se filtre, id, nombre, ubicacion, limite de personas y personas actuales
     * Accesible para usuarios con roles ADMIN o VISOR
     *
     * @return lista de todas las oficinas en la base de datos
     */
    @GetMapping("/id/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','VISOR')")
    public ResponseEntity<Oficina> obtenerPorId(@PathVariable Integer id){
        Optional<Oficina> oficina = oficinaRepository.findById(id);
        return oficina.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/nombre")
    @PreAuthorize("hasAnyRole('ADMIN','VISOR')")
    public ResponseEntity<List<Oficina>> obtenerOficinaNombre(@RequestParam String nombre){
        List<Oficina> listado = oficinaRepository.buscarPorNombre(nombre);
        return ResponseEntity.ok(listado);
    }
    @GetMapping("/ubicacion")
    @PreAuthorize("hasAnyRole('ADMIN','VISOR')")
    public ResponseEntity<List<Oficina>> obtenerOficinaUbicacion(@RequestParam String ubicacion){
        List<Oficina> listado = oficinaRepository.buscarPorUbicacion(ubicacion);
        return ResponseEntity.ok(listado);
    }
    @GetMapping("/limitePersonas")
    @PreAuthorize("hasAnyRole('ADMIN','VISOR')")
    public ResponseEntity<List<Oficina>> obtenerOficinaLimitePersonas(@RequestParam int limitePersonas){
        List<Oficina> listado = oficinaRepository.buscarPorLimitePersonas(limitePersonas);
        return ResponseEntity.ok(listado);
    }
    @GetMapping("/personasActuales")
    @PreAuthorize("hasAnyRole('ADMIN','VISOR')")
    public ResponseEntity<List<Oficina>> obtenerOficinaPersonasActuales(@RequestParam int personasActuales){
        List<Oficina> listado = oficinaRepository.buscarPorPersonasActuales(personasActuales);
        return ResponseEntity.ok(listado);
    }
}
