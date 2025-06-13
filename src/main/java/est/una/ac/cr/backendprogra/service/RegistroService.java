package est.una.ac.cr.backendprogra.service;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import est.una.ac.cr.backendprogra.records.registro.DatosActualizarRegistro;
import est.una.ac.cr.backendprogra.records.registro.DatosAgregarRegistro;
import jakarta.servlet.http.HttpServletResponse;
import est.una.ac.cr.backendprogra.entidad.Oficina;
import est.una.ac.cr.backendprogra.entidad.Persona;
import est.una.ac.cr.backendprogra.entidad.Registro;
import est.una.ac.cr.backendprogra.repository.OficinaRepository;
import est.una.ac.cr.backendprogra.repository.PersonaRepository;
import est.una.ac.cr.backendprogra.repository.RegistroRepository;
import est.una.ac.cr.backendprogra.validaciones.registro.ValidacionRegistro;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
/**
 * Servicio que gestiona las operaciones relacionadas con los registros
 * Permite registrar, buscar, actualizar y eliminar registros en el sistema
 *  * aqui se definen todos los metodos que luego serán llamados en el controlador
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@Service
public class RegistroService {

    @Autowired
    private RegistroRepository registroRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private OficinaRepository oficinaRepository;

    @Autowired
    ValidacionRegistro validacionRegistro;

    public List<Registro> listarRegistros(){
        return registroRepository.findAll();
    }

    /**
     * Registra una nueva entrada o salida de una persona, actualizando el conteo de personas en la oficina
     *
     * @param registroDTO DTO con los datos para crear el registro
     * @return el nuevo Registro guardado en la base de datos
     * @throws RuntimeException si la Persona no existe o la validación del registro falla
     */
    public Registro ingresoRegitro(DatosAgregarRegistro registroDTO){
        Persona persona = personaRepository.findById(registroDTO.personaId()).orElseThrow(() ->new RuntimeException("Persona no encontrada"));

        Oficina oficina = persona.getOficina();

        Registro nuevoRegistro = new Registro();
        nuevoRegistro.setPersona(persona);
        nuevoRegistro.setTipo(registroDTO.tipo());
        nuevoRegistro.setFechaHora(registroDTO.fechaHora());

        validacionRegistro.validar(nuevoRegistro);
        if ("Entrada".equalsIgnoreCase(registroDTO.tipo())) {
            oficina.setPersonasActuales(oficina.getPersonasActuales() + 1);
        } else if ("Salida".equalsIgnoreCase(registroDTO.tipo())) {
            oficina.setPersonasActuales(oficina.getPersonasActuales() - 1);
        }
        return registroRepository.save(nuevoRegistro);
    }
    /**
     * Actualiza un registro existente con nuevos datos, validando la información antes de guardarla
     *
     * @param id           Identificador del registro a actualizar
     * @param registroDTO  DTO con los datos actualizados del registro
     * @return             El registro actualizado y guardado en la base de datos
     * @throws RuntimeException si el registro o la persona relacionada no se encuentran o la validación falla
     */
    public Registro actualizarRegistro(Integer id, DatosActualizarRegistro registroDTO){
        Registro registroExistente = registroRepository.findById(id).orElseThrow(() ->new RuntimeException("Registro no encontrado"));
        Persona persona = personaRepository.findById(registroDTO.personaId()).orElseThrow(() ->new RuntimeException("Persona no encontrada"));

        registroExistente.setPersona(persona);
        registroExistente.setFechaHora(registroDTO.fechaHora());

        validacionRegistro.validar(registroExistente);
        return registroRepository.save(registroExistente);
    }

    /**
     * Elimina un registro de entrada o salida de una persona y ajusta el conteo actual de personas en la oficina
     *
     * <p>Si el registro eliminado es una entrada y no existen salidas posteriores para la persona,
     * se decrementa el número de personas actuales en la oficina (sin caer por debajo de cero)
     * Si el registro es una salida y existen entradas previas para la persona se incrementa el número de personas actuales en la oficina.</p>
     *
     * @param id El identificador del registro a eliminar.
     * @throws RuntimeException si el registro no se encuentra.
     */
    public void eliminarRegistro(Integer id){
        Registro registro = registroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado"));
        Persona persona = registro.getPersona();
        Oficina oficina = persona.getOficina();
        // Verificar si el tipo es "Entrada"
        if ("Entrada".equalsIgnoreCase(registro.getTipo())) {
            boolean tieneSalidaPosterior = registroRepository.existsByPersonaIdAndTipoAndFechaHoraAfter(
                    persona.getId(), "Salida", registro.getFechaHora());

            if (!tieneSalidaPosterior) {
                oficina.setPersonasActuales(Math.max(0, oficina.getPersonasActuales() - 1));
            }
        }
        // Verificar si el tipo es "Salida"
        else if ("Salida".equalsIgnoreCase(registro.getTipo())) {
            boolean tieneEntradaAnterior = registroRepository.existsByPersonaIdAndTipoAndFechaHoraBefore(
                    persona.getId(), "Entrada", registro.getFechaHora());

            if (tieneEntradaAnterior) {
                oficina.setPersonasActuales(oficina.getPersonasActuales() + 1);
            }
        }
        oficinaRepository.save(oficina);
        registroRepository.delete(registro);
    }





    //exportaciones
    /**
     * Exporta la lista de registros a un archivo Excel y lo envía en la respuesta HTTP
     *
     * @param response la respuesta HTTP para enviar el archivo.
     * @throws IOException si ocurre un error durante la escritura del archivo.
     */
    public void exportarExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=registros.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Registros");

        List<Registro> registros = registroRepository.findAll();

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Persona");
        header.createCell(2).setCellValue("Tipo");
        header.createCell(3).setCellValue("Fecha y Hora");
        for(int i = 0; i<registros.size(); i++){
            Registro r = registros.get(i);
            Row row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(r.getId());
            row.createCell(1).setCellValue(r.getPersona().getId());
            row.createCell(2).setCellValue(r.getTipo());
            row.createCell(3).setCellValue(r.getFechaHora());
        }
        workbook.write(response.getOutputStream());
        workbook.close();
    }
    /**
     * Exporta la lista de registros a un archivo pdf y lo envía en la respuesta HTTP
     *
     * @param response la respuesta HTTP para enviar el archivo.
     * @throws IOException si ocurre un error durante la escritura del archivo.
     */
    public void exportarPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=registros.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        Table table = new Table(new float[]{1, 3, 2, 3});
        table.addHeaderCell(new Cell().add(new Paragraph("ID")));
        table.addHeaderCell(new Cell().add(new Paragraph("Persona")));
        table.addHeaderCell(new Cell().add(new Paragraph("Tipo")));
        table.addHeaderCell(new Cell().add(new Paragraph("Fecha y Hora")));

        List<Registro> registros = registroRepository.findAll();
        for (Registro r : registros) {
            table.addCell(String.valueOf(r.getId()));
            table.addCell(r.getPersona().getNombre());
            table.addCell(r.getTipo());
            table.addCell(r.getFechaHora().toString());
        }
        document.add(table);
        document.close();
    }

}
