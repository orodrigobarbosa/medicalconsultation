package com.medicalconsultation.demo.consulta.resources;

import com.medicalconsultation.demo.consulta.domain.Consulta;
import com.medicalconsultation.demo.consulta.services.ConsultaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RequestMapping(value = "/consulta")
@RestController
public class ConsultaController {

    private ConsultaService consultaService;

    @PostMapping
    public ResponseEntity<Consulta> cadastrarConsulta(@RequestBody Consulta consulta) {
        Consulta novaConsulta = consultaService.cadastrarConsulta(consulta);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(novaConsulta.getIdConsulta()).toUri();
        return ResponseEntity.created(uri).body(novaConsulta);
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> listarConsultas() {
        List<Consulta> consultas = consultaService.listarConsultas();
        return ResponseEntity.ok().body(consultas);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Consulta> buscarConsulta(@PathVariable Long id) {
        Consulta consulta = consultaService.buscarConsulta(id);
        return ResponseEntity.ok().body(consulta);
    }

    @DeleteMapping(value = "/deletar/{id}")
    public ResponseEntity<Void> deletarConsulta(@PathVariable Long id) {
        consultaService.deletarConsulta(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/atualizar/{id}")
    public ResponseEntity<Consulta> atualizarConsultaPorId(@PathVariable Long id, @RequestBody Consulta consulta) {
        Consulta consultaAtualizada = consultaService.atualizarConsulta(consulta, id);
        return ResponseEntity.ok().body(consultaAtualizada);
    }

}
