package est.una.ac.cr.backendprogra.service;



import est.una.ac.cr.backendprogra.entidad.Persona;
import est.una.ac.cr.backendprogra.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    public List<Persona> listarPersonas() {
        return personaRepository.findAll();
    }
}