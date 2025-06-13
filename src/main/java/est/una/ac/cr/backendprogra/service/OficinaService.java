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
/**
 * Servicio que gestiona las operaciones relacionadas con las oficinas
 * Permite registrar, buscar, actualizar y eliminar oficinas en el sistema
 * * aqui se definen todos los metodos que luego serán llamados en el controlador
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@Service
public class OficinaService {

    @Autowired
    private OficinaRepository oficinaRepository;
    @Autowired
    ValidacionOficina validacionOficina;

    public List<Oficina> listarOficinas() { return oficinaRepository.findAll(); }

    /**
     * Crea y guarda una nueva oficina a partir de los datos proporcionados
     *
     * Crea un nuevo objeto Oficina
     * Asigna nombre, ubicación y límite de personas desde el DTO recibido
     * Inicializa el conteo de personas actuales en 0
     * Valida la oficina con las reglas definidas en `validacionOficina`
     * Guarda la oficina en la base de datos mediante `oficinaRepository`
     *
     * @param oficinaDTO datos necesarios para crear la oficina
     * @return la oficina guardada con su ID asignado
     * @throws RuntimeException si la validación falla
     */
    public Oficina ingresoOficina(DatosRegistroOficina oficinaDTO) {
        Oficina oficina = new Oficina();
        oficina.setNombre(oficinaDTO.nombre());
        oficina.setUbicacion(oficinaDTO.ubicacion());
        oficina.setLimitePersonas(oficinaDTO.limitePersonas());
        oficina.setPersonasActuales(0);

        validacionOficina.validar(oficina);
        return oficinaRepository.save(oficina);
    }

    /**
     * Actualiza los datos de una oficina existente identificada por su ID
     *
     *
     * Busca la oficina en la base de datos, lanzando excepción si no existe
     * Actualiza nombre, ubicación y límite de personas con los datos del DTO recibido
     * Valida la oficina actualizada con `validacionOficina`
     * Guarda los cambios en la base de datos
     *
     * @param id el identificador de la oficina a actualizar
     * @param oficinaDTO datos nuevos para actualizar la oficina
     * @return la oficina actualizada
     * @throws RuntimeException si la oficina no existe o la validación falla
     */
    public Oficina actualizaOficina(Integer id, DatosActualizarOficina oficinaDTO) {
        Oficina oficina = oficinaRepository.findById(id).orElseThrow(() -> new RuntimeException("Oficina no encontrada"));

        oficina.setNombre(oficinaDTO.nombre());
        oficina.setUbicacion(oficinaDTO.ubicacion());
        oficina.setLimitePersonas(oficinaDTO.limitePersonas());
        validacionOficina.validar(oficina);
        return oficinaRepository.save(oficina);
    }
    /**
     * Exporta la lista de oficinas a un archivo Excel y lo envía en la respuesta HTTP
     *
     * @param response la respuesta HTTP para enviar el archivo.
     * @throws IOException si ocurre un error durante la escritura del archivo.
     */
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
    /**
     * Exporta la lista de oficinas a un archivo pdf y lo envía en la respuesta HTTP
     *
     * @param response la respuesta HTTP para enviar el archivo.
     * @throws IOException si ocurre un error durante la escritura del archivo.
     */
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
