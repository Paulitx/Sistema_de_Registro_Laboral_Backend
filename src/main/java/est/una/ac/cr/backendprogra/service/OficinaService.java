package est.una.ac.cr.backendprogra.service;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import est.una.ac.cr.backendprogra.entidad.Oficina;
import est.una.ac.cr.backendprogra.records.oficina.DatosActualizarOficina;
import est.una.ac.cr.backendprogra.records.oficina.DatosRegistroOficina;
import est.una.ac.cr.backendprogra.repository.OficinaRepository;
import est.una.ac.cr.backendprogra.validaciones.oficina.ValidacionOficina;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class OficinaService {

    @Autowired
    private OficinaRepository oficinaRepository;
    @Autowired
    ValidacionOficina validacionOficina;

    public List<Oficina> listarOficinas() { return oficinaRepository.findAll(); }

    public Oficina ingresoOficina(DatosRegistroOficina oficinaDTO) {
        Oficina oficina = new Oficina();
        oficina.setNombre(oficinaDTO.nombre());
        oficina.setUbicacion(oficinaDTO.ubicacion());
        oficina.setLimitePersonas(oficinaDTO.limitePersonas());
        oficina.setPersonasActuales(0);

        validacionOficina.validar(oficina);
        return oficinaRepository.save(oficina);
    }
    public Oficina actualizaOficina(Integer id, DatosActualizarOficina oficinaDTO) {
        Oficina oficina = oficinaRepository.findById(id).orElseThrow(() -> new RuntimeException("Oficina no encontrada"));

        oficina.setNombre(oficinaDTO.nombre());
        oficina.setUbicacion(oficinaDTO.ubicacion());
        oficina.setLimitePersonas(oficinaDTO.limitePersonas());
        validacionOficina.validar(oficina);
        return oficinaRepository.save(oficina);
    }

    public void exportarExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=oficinas.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Oficinas");

        List<Oficina> oficinas = oficinaRepository.findAll();

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Nombre");
        header.createCell(2).setCellValue("Ubicación");
        header.createCell(3).setCellValue("Límite de Personas");
        header.createCell(4).setCellValue("Personas Actuales");

        for (int i = 0; i < oficinas.size(); i++) {
            Oficina o = oficinas.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(o.getId());
            row.createCell(1).setCellValue(o.getNombre());
            row.createCell(2).setCellValue(o.getUbicacion());
            row.createCell(3).setCellValue(o.getLimitePersonas());
            row.createCell(4).setCellValue(o.getPersonasActuales());
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    public void exportarPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=oficinas.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        Table table = new Table(new float[]{1, 3, 3, 2, 2});
        table.addHeaderCell(new Cell().add(new Paragraph("ID")));
        table.addHeaderCell(new Cell().add(new Paragraph("Nombre")));
        table.addHeaderCell(new Cell().add(new Paragraph("Ubicación")));
        table.addHeaderCell(new Cell().add(new Paragraph("Límite")));
        table.addHeaderCell(new Cell().add(new Paragraph("Actuales")));

        List<Oficina> oficinas = oficinaRepository.findAll();
        for (Oficina o : oficinas) {
            table.addCell(String.valueOf(o.getId()));
            table.addCell(o.getNombre());
            table.addCell(o.getUbicacion());
            table.addCell(String.valueOf(o.getLimitePersonas()));
            table.addCell(String.valueOf(o.getPersonasActuales()));
        }

        document.add(table);
        document.close();
    }

}
