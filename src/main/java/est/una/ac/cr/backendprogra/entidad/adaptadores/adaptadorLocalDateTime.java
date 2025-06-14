package est.una.ac.cr.backendprogra.entidad.adaptadores;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Adaptador JAXB para convertir entre {@link LocalDateTime} y su representación en cadena de texto (String) en formato ISO-8601 sin zona horaria (yyyy-MM-dd'T'HH:mm:ss)
 *
 * <p>Este adaptador se utiliza durante la serialización (marshal) y deserialización (unmarshal) de objetos JAXB, permitiendo que campos de tipo
 * {@code LocalDateTime} se representen correctamente en XML.</p>
 *
 * <p>Ejemplo de formato manejado: "2025-06-14T20:45:30"</p>
 *
 * @author TuNombre
 * @version 1.0
 */
public class adaptadorLocalDateTime extends XmlAdapter<String, LocalDateTime> {


    /**
     * Formateador para fecha y hora en formato ISO local date-time (yyyy-MM-dd'T'HH:mm:ss).
     */
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


    /**
     * Convierte una cadena de texto en formato ISO local date-time a un objeto {@link LocalDateTime}.
     *
     * @param v la cadena de texto con la fecha y hora en formato "yyyy-MM-dd'T'HH:mm:ss"
     * @return el objeto {@code LocalDateTime} correspondiente, o {@code null} si la cadena es {@code null} o vacía
     * @throws Exception si el formato de la cadena es inválido y no puede ser parseado
     */
    @Override
    public LocalDateTime unmarshal(String v) throws Exception {
        return (v == null || v.isEmpty()) ? null : LocalDateTime.parse(v, formatter);
    }

    /**
     * Convierte un objeto {@link LocalDateTime} a su representación en cadena de texto en formato ISO local date-time.
     *
     * @param v el objeto {@code LocalDateTime} a convertir
     * @return la cadena de texto con la fecha y hora en formato "yyyy-MM-dd'T'HH:mm:ss", o {@code null} si el objeto es {@code null}
     * @throws Exception si ocurre un error durante la conversión
     */
    @Override
    public String marshal(LocalDateTime v) throws Exception {
        return (v == null) ? null : v.format(formatter);
    }
}
