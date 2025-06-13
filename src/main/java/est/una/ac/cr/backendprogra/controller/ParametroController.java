package est.una.ac.cr.backendprogra.controller;


import est.una.ac.cr.backendprogra.entidad.Parametro;
import est.una.ac.cr.backendprogra.repository.ParametroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 *  * Controllador de parametro, permite todas las interacciones con el usuairo, gets, post, put y delete.
 *
 * @author Luis Felipe Méndez González-Paula Vargas Campos
 */
@RestController
@RequestMapping("/api/parametro")
public class ParametroController {

    @Autowired
    private ParametroRepository parametroRepository;

    @PostMapping
    public Parametro crearParametro(@RequestBody Parametro parametro) {return parametroRepository.save(parametro);
    }

    @GetMapping
    public List<Parametro> obtenerTodas(){
        return parametroRepository.findAll();
    }
}
