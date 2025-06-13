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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 *  * Controlador de registro, permite todas las interacciones con el usuairo, gets, post, put y delete.
 * tambien lo restringe segun su rol para evitar que usuarios no autorizados entren a secciones que no deben
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@RestController
@RequestMapping("/api/registro")
public class RegistroController {

    @Autowired
    private RegistroRepository registroRepository;
    @Autowired
    private RegistroService registroService;


    /**
     * Obtiene la lista completa de registros
     * Accesible para usuarios con roles ADMIN o VISOR
     *
     * @return lista de todas las registros en la base de datos
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRADOR', 'VISOR')")
    public ResponseEntity<List<Registro>> obtenerTodosRegistro() {
        List<Registro> registros = registroRepository.findAll();
        return ResponseEntity.ok(registros);
    }


    /**
     * Obtiene una página de registro con paginación y orden descendente por id
     * Accesible para usuarios con roles ADMIN o VISOR
     *
     * @param page número de página (0 por defecto)
     * @param size tamaño de página (5 por defecto)
     * @return ResponseEntity con la página de registro solicitada
     */
    @GetMapping("/paginacion")
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRADOR', 'VISOR')")
    public ResponseEntity<Page<Registro>> obtenerRegistros(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaHora").descending());
        Page<Registro> registrosPaginados = registroRepository.findAll(pageable);
        return ResponseEntity.ok(registrosPaginados);
    }
    /**
     * Crea una nueva registro en la base de datos
     * Solo accesible para usuarios con rol ADMIN
     *
     * @param registro datos para registrar un nuevo registro
     * @return ResponseEntity con la registro creada o error en caso de excepción
     */

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRADOR')")
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
    /**
     * Actualiza un registro existente identificada por su id
     * Solo accesible para usuarios con rol ADMIN
     *
     * @param id identificador del registro a actualizar
     * @param registroExistente datos para actualizar el registro
     * @return ResponseEntity con el registro actualizada o error en caso de excepción
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRADOR')")
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
    /**
     * Elimina un registro por su id
     * Solo accesible para usuarios con rol ADMIN
     *
     * @param id identificador del registro a eliminar
     * @return ResponseEntity sin contenido (204) si se elimina o si no existe
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRADOR')")
    public ResponseEntity<Void> eliminarRegistro(@PathVariable Integer id) {
        try {
            registroService.eliminarRegistro(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    /**
     * Obtiene la lista completa de registros en excel
     * Accesible para usuarios con roles ADMIN o VISOR
     *
     * @return lista de todas las registros en la base de datos
     */
    @GetMapping("/exportar/excel")
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRADOR', 'VISOR')")
    public void exportarExcel(HttpServletResponse response) throws IOException {
        registroService.exportarExcel(response);
    }
    /**
     * Obtiene la lista completa de registros en pdf
     * Accesible para usuarios con roles ADMIN o VISOR
     *
     * @return lista de todas las registros en la base de datos
     */
    @GetMapping("/exportar/pdf")
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRADOR', 'VISOR')")
    public void exportarPDF(HttpServletResponse response) throws IOException {
        registroService.exportarPDF(response);
    }



    /**
     * Obtiene la lista completa de registros dependiendo de porque se filtre, id, tipo, fecha y hora y por el id de las personas
     * Accesible para usuarios con roles ADMIN o VISOR
     *
     * @return lista de todas las registros en la base de datos
     */
    @GetMapping("/id/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRADOR', 'VISOR')")
    public ResponseEntity<Registro> obtenerPorId(@PathVariable Integer id) {
        Optional<Registro> registro = registroRepository.findById(id);
        return registro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/tipo")
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRADOR', 'VISOR')")
    public ResponseEntity<List<Registro>> obtenerRegistroTipo(@RequestParam String tipo){
        List<Registro> listado = registroRepository.buscarPorTipoRegitro(tipo);
        return ResponseEntity.ok(listado);
    }
    @GetMapping("/fechaHora")
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRADOR', 'VISOR')")
    public ResponseEntity<List<Registro>> obtenerRegistroFechaHora(@RequestParam LocalDateTime fechaHora){
        List<Registro> listado = registroRepository.buscarPorFechaHoraExacta(fechaHora);
        return ResponseEntity.ok(listado);
    }
    @GetMapping("/persona")
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRADOR', 'VISOR')")
    public ResponseEntity<List<Registro>> obtenerRegistroTelefono(@RequestParam Integer personaId){
        List<Registro> listado = registroRepository.buscarPorIdPersona(personaId);
        return ResponseEntity.ok(listado);
    }
}