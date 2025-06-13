package est.una.ac.cr.backendprogra.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 *  * Entidad Parametro, permite la creacion de tablas en la base de datos, ademas de ser la base de informacion de Parametro
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Parametro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String llave;
    private String valor;

    /**
     * Métodos getters y setters para los atributos de la entidad
     * Permiten acceder y modificar:
     * id: Identificador único del objeto
     * llave: Clave o nombre del atributo
     * valor: Valor asociado a la clave
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLlave() {
        return llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
