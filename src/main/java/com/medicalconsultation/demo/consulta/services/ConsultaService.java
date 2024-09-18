package com.medicalconsultation.demo.consulta.services;

import com.medicalconsultation.demo.consulta.domain.Consulta;
import com.medicalconsultation.demo.consulta.repositories.ConsultaRepository;
import com.medicalconsultation.demo.usuario.domain.Usuario;
import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ConsultaService {



    private ConsultaRepository consultaRepository;

    public Consulta cadastrarConsulta(Consulta consulta) {
        consulta.setIdConsulta(null);
        return consultaRepository.save(consulta);
    }

    public Consulta buscarConsulta(Long id) {
        return consultaRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Consulta não encontrado! ID: ", id));
    }

    public List<Consulta> listarConsultas() {
        return consultaRepository.findAll();
    }

    public void deletarConsulta(Long id){
        Consulta consulta = buscarConsulta(id);
        consultaRepository.delete(consulta);
    }

    public Consulta atualizarConsulta(Consulta consulta, Long id) {
        Consulta upConsulta = buscarConsulta(id);
        upConsulta.setDataConsulta(consulta.getDataConsulta());
        upConsulta.setProfissional(consulta.getProfissional());
        upConsulta.setEspecialidade(consulta.getEspecialidade());
        return consultaRepository.save(upConsulta);
    }
}
