package est.una.ac.cr.backendprogra.service;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
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

    public Registro ingresoRegitro(Integer personaId, String tipo, LocalDateTime fechaHora){
        Persona persona = personaRepository.findById(personaId).orElseThrow(() ->new RuntimeException("Persona no encontrada"));

        Oficina oficina=persona.getOficina();
        validacionRegistro.validar(personaId, oficina.getId(), tipo);

        Registro nuevoRegistro = new Registro();
        nuevoRegistro.setPersona(persona);
        nuevoRegistro.setTipo(tipo);
        nuevoRegistro.setFechaHora(fechaHora);

        if ("Entrada".equalsIgnoreCase(tipo)) {
            oficina.setPersonasActuales(oficina.getPersonasActuales() + 1);
        } else if ("Salida".equalsIgnoreCase(tipo)) {
            oficina.setPersonasActuales(oficina.getPersonasActuales() - 1);
        }
        return registroRepository.save(nuevoRegistro);
    }

    public void eliminarRegistro(Integer id){
        Registro registro = registroRepository.findById(id).orElseThrow(() -> new RuntimeException("Registro no encontrado"));
        Persona persona = registro.getPersona();
        Oficina oficina = persona.getOficina();
        if("Entrada".equalsIgnoreCase((registro.getTipo()))){
            oficina.setPersonasActuales(Math.max(0, oficina.getPersonasActuales() - 1));
        }else if("Salida".equalsIgnoreCase((registro.getTipo()))){
            oficina.setPersonasActuales(oficina.getPersonasActuales() + 1);
        }
        oficinaRepository.save(oficina);
        registroRepository.delete(registro);
    }
    //actualizar si hace falta




    //exportaciones

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
