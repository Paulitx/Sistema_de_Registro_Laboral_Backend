package est.una.ac.cr.backendprogra.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import est.una.ac.cr.backendprogra.entidad.Oficina;
import est.una.ac.cr.backendprogra.entidad.Persona;
import est.una.ac.cr.backendprogra.records.oficina.DatosActualizarOficina;
import est.una.ac.cr.backendprogra.records.persona.DatosActualizarPersona;
import est.una.ac.cr.backendprogra.records.persona.DatosPersona;
import est.una.ac.cr.backendprogra.records.persona.DatosRegistroPersona;
import est.una.ac.cr.backendprogra.repository.OficinaRepository;
import est.una.ac.cr.backendprogra.repository.PersonaRepository;
import est.una.ac.cr.backendprogra.validaciones.persona.ValidacionPersona;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
/**
 * Servicio que gestiona las operaciones relacionadas con las personas
 * Permite registrar, buscar, actualizar y eliminar personas en el sistema
 * aqui se definen todos los metodos que luego serán llamados en el controlador
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private OficinaRepository oficinaRepository;

    @Autowired
    ValidacionPersona validacionPersona;

    public List<Persona> listarPersonas() {
        return personaRepository.findAll();
    }
    /**
     * Asigna los datos del DTO a la entidad Persona y asocia la Oficina correspondiente
     *
     * @param persona  la instancia de Persona a actualizar
     * @param personaDTO  el DTO con los datos a asignar
     * @return la Persona con los datos actualizados
     * @throws RuntimeException si la Oficina no se encuentra
     */
    private Persona seteoDatos(Persona persona, DatosPersona personaDTO) {
        Oficina oficina = oficinaRepository.findById(personaDTO.oficina()).orElseThrow(() -> new RuntimeException("Oficina no encontrada"));

        persona.setIdUsuario(personaDTO.idUsuario());
        persona.setNombre(personaDTO.nombre());
        persona.setEmail(personaDTO.email());
        persona.setDireccion(personaDTO.direccion());
        persona.setFechaNacimiento(personaDTO.fechaNacimiento());
        persona.setTelefono(personaDTO.telefono());
        persona.setCargo(personaDTO.cargo());
        persona.setEstado(personaDTO.estado());
        persona.setOficina(oficina);

        return persona;
    }

    /**
     * Crea y guarda una nueva Persona en la base de datos después de validar los datos
     *
     * @param personaDTO  DTO con los datos para crear la Persona
     * @return la Persona creada y guardada
     * @throws RuntimeException si la validación falla o la Oficina no se encuentra
     */
    public Persona ingresarPersona(DatosRegistroPersona personaDTO) {
        Persona persona = seteoDatos(new Persona(), personaDTO);
        validacionPersona.validar(persona);
        return personaRepository.save(persona);
    }
    /**
     * Actualiza una Persona existente con nuevos datos, valida y guarda los cambios
     *
     * @param id  identificador de la Persona a actualizar
     * @param personaDTO  DTO con los datos actualizados
     * @return la Persona actualizada y guardada
     * @throws RuntimeException si la Persona no existe, la validación falla o la Oficina no se encuentra
     */
    public Persona actualizaPersona(Integer id, DatosActualizarPersona personaDTO) {
        Persona persona = personaRepository.findById(id).orElseThrow(() -> new RuntimeException("Persona no encontrada"));
        Persona personaActualizada = seteoDatos(persona, personaDTO);
        validacionPersona.validar(personaActualizada);
        return personaRepository.save(personaActualizada);
    }


    ///exportaciones
    /**
     * Exporta la lista de personas a un archivo Excel y lo envía en la respuesta HTTP
     *
     * @param response la respuesta HTTP para enviar el archivo.
     * @throws IOException si ocurre un error durante la escritura del archivo.
     */
    public void exportarExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=personas.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Personas");

        List<Persona> personas = personaRepository.findAll();

        // Encabezados
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("ID Usuario");
        header.createCell(2).setCellValue("Nombre");
        header.createCell(3).setCellValue("Email");
        header.createCell(4).setCellValue("Dirección");
        header.createCell(5).setCellValue("Fecha de Nacimiento");
        header.createCell(6).setCellValue("Teléfono");
        header.createCell(7).setCellValue("Cargo");
        header.createCell(8).setCellValue("Estado");

        // Datos
        for (int i = 0; i < personas.size(); i++) {
            Persona p = personas.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(p.getId());
            row.createCell(1).setCellValue(p.getIdUsuario());
            row.createCell(2).setCellValue(p.getNombre());
            row.createCell(3).setCellValue(p.getEmail());
            row.createCell(4).setCellValue(p.getDireccion());
            row.createCell(5).setCellValue(p.getFechaNacimiento().toString());
            row.createCell(6).setCellValue(p.getTelefono());
            row.createCell(7).setCellValue(p.getCargo());
            row.createCell(8).setCellValue(p.getEstado() ? "Activo" : "Inactivo");
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
    /**
     * Exporta la lista de personas a un archivo pdf y lo envía en la respuesta HTTP
     *
     * @param response la respuesta HTTP para enviar el archivo.
     * @throws IOException si ocurre un error durante la escritura del archivo.
     */
    public void exportarPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=personas.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        float[] columnWidths = {1, 2, 2, 3, 3, 2, 2, 2, 1};
        Table table = new Table(columnWidths);

        // Encabezados
        table.addHeaderCell("ID");
        table.addHeaderCell("ID Usuario");
        table.addHeaderCell("Nombre");
        table.addHeaderCell("Email");
        table.addHeaderCell("Dirección");
        table.addHeaderCell("Fecha Nac.");
        table.addHeaderCell("Teléfono");
        table.addHeaderCell("Cargo");
        table.addHeaderCell("Estado");

        List<Persona> personas = personaRepository.findAll();
        for (Persona p : personas) {
            table.addCell(p.getId().toString());
            table.addCell(p.getIdUsuario());
            table.addCell(p.getNombre());
            table.addCell(p.getEmail());
            table.addCell(p.getDireccion());
            table.addCell(p.getFechaNacimiento().toString());
            table.addCell(p.getTelefono());
            table.addCell(p.getCargo());
            table.addCell(p.getEstado() ? "Activo" : "Inactivo");
        }

        document.add(table);
        document.close();
    }

}