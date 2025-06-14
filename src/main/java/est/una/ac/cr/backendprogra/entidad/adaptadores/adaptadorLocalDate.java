package est.una.ac.cr.backendprogra.entidad.adaptadores;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Adaptador JAXB para convertir entre {@link LocalDate} y su representación
 * en cadena de texto (String) en formato ISO-8601 (yyyy-MM-dd).
 *
 * <p>Este adaptador se utiliza durante la serialización (marshal) y
 * deserialización (unmarshal) de objetos JAXB para que los campos de tipo
 * {@code LocalDate} se transformen correctamente en XML y viceversa.</p>
 *
 *
 * @author Luis Felipe Méndez González - Paula Vargas Campos

 */
public class adaptadorLocalDate extends XmlAdapter<String, LocalDate> {

    /**
     * Formateador para fecha en formato ISO local date (yyyy-MM-dd).
     */
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * Convierte una cadena de texto en formato ISO local date a un objeto {@link LocalDate}.
     *
     * @param v la cadena de texto con la fecha en formato "yyyy-MM-dd"
     * @return el objeto {@code LocalDate} correspondiente, o {@code null} si la cadena es {@code null} o vacía
     * @throws Exception si el formato de la cadena es inválido y no puede ser parseado
     */
    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return (v == null || v.isEmpty()) ? null : LocalDate.parse(v, formatter);
    }

    /**
     * Convierte un objeto {@link LocalDate} a su representación en cadena de texto en formato ISO local date
     *
     * @param v el objeto {@code LocalDate} a convertir
     * @return la cadena de texto con la fecha en formato "yyyy-MM-dd", o {@code null} si el objeto es {@code null}
     * @throws Exception si ocurre un error durante la conversión
     */
    @Override
    public String marshal(LocalDate v) throws Exception {
        return (v == null) ? null : v.format(formatter);
    }
}
