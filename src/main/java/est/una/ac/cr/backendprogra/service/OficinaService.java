package est.una.ac.cr.backendprogra.service;

import est.una.ac.cr.backendprogra.entidad.Oficina;
import est.una.ac.cr.backendprogra.records.oficina.DatosActualizarOficina;
import est.una.ac.cr.backendprogra.records.oficina.DatosRegistroOficina;
import est.una.ac.cr.backendprogra.repository.OficinaRepository;
import est.una.ac.cr.backendprogra.validaciones.oficina.ValidacionOficina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
