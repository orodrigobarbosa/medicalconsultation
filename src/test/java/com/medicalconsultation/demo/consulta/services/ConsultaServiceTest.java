package com.medicalconsultation.demo.consulta.services;

import com.medicalconsultation.demo.consulta.domain.Consulta;
import com.medicalconsultation.demo.consulta.repositories.ConsultaRepository;
import com.medicalconsultation.demo.exceptions.ExceptionDataIntegrityViolation;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsultaServiceTest {

    @InjectMocks
    private ConsultaService consultaService;

    @Mock
    private ConsultaRepository consultaRepository;

    // Teste cadastrar consulta
    @Test
    void cadastrarConsulta() {
        // Dados de entrada
        Consulta consulta = new Consulta();
        consulta.setDescricao("Consulta médica");

        // Simulação do repositório
        when(consultaRepository.save(any(Consulta.class))).thenAnswer(invocation -> {
            Consulta consultaSalva = invocation.getArgument(0);
            consultaSalva.setIdConsulta(1L); // Define um ID simulado após o save
            return consultaSalva;
        });

        // Execução do método
        Consulta resultado = consultaService.cadastrarConsulta(consulta);

        // Validação do teste
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdConsulta());
        assertEquals("Consulta médica", resultado.getDescricao());
        verify(consultaRepository, times(1)).save(consulta);
    }

    // Teste listar consultas
    @Test
    void listarConsultas() {
        // Dados de entrada
        Consulta consulta1 = new Consulta();
        consulta1.setIdConsulta(1L);
        consulta1.setDescricao("Consulta médica 1");

        Consulta consulta2 = new Consulta();
        consulta2.setIdConsulta(2L);
        consulta2.setDescricao("Consulta médica 2");

        List<Consulta> consultasEsperadas = Arrays.asList(consulta1, consulta2);

        // Simulação do repositório
        when(consultaRepository.findAll()).thenReturn(consultasEsperadas);

        // Execução do método
        List<Consulta> resultado = consultaService.listarConsultas();

        // Validação do teste
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Consulta médica 1", resultado.get(0).getDescricao());
        assertEquals("Consulta médica 2", resultado.get(1).getDescricao());
        verify(consultaRepository, times(1)).findAll();
    }

    // Teste buscar consulta exige dois cenários: se existe ou não
    @Test
    void buscarConsulta_ConsultaExistente() {
        // Dados de entrada
        Long idConsulta = 1L;
        Consulta consulta = new Consulta();
        consulta.setIdConsulta(idConsulta);
        consulta.setDescricao("Consulta médica");

        // Configuração do comportamento do mock
        when(consultaRepository.findById(idConsulta)).thenReturn(Optional.of(consulta));

        // Execução do método
        Consulta resultado = consultaService.buscarConsulta(idConsulta);

        // Validação do teste
        assertNotNull(resultado);
        assertEquals(idConsulta, resultado.getIdConsulta());
        assertEquals("Consulta médica", resultado.getDescricao());
        verify(consultaRepository, times(1)).findById(idConsulta);
    }

    // Cenário 02, se não existe a consulta
    @Test
    void buscarConsulta_ConsultaNaoExistente() {
        // Dados de entrada
        Long idConsulta = 1L;

        // Configuração do comportamento do mock
        when(consultaRepository.findById(idConsulta)).thenReturn(Optional.empty());

        // Execução do método a ser testado e validação da exceção
        Exception exception = assertThrows(ObjectNotFoundException.class, () ->
                consultaService.buscarConsulta(idConsulta)
        );

        assertEquals("Consulta não encontrado! ID: ", exception.getMessage());
        verify(consultaRepository, times(1)).findById(idConsulta);
    }

    // Teste unitário de deletar consulta. 3 Cenários: Se existe ou não, e teste de violação de integridade
    @Test
    void deletarConsulta_ConsultaExistente() {
        // Dados de entrada
        Long idConsulta = 1L;

        // Simulação do comportamento do repositório
        when(consultaRepository.findById(idConsulta)).thenReturn(Optional.of(new Consulta()));

        // Execução do método
        consultaService.deletarConsulta(idConsulta);

        // Validação do teste
        verify(consultaRepository, times(1)).deleteById(idConsulta);
    }

    @Test
    void deletarConsulta_ConsultaNaoExistente() {
        // Dados de entrada
        Long idConsulta = 1L;

        // Simulação do comportamento do repositório
        when(consultaRepository.findById(idConsulta)).thenReturn(Optional.empty());

        // Execução do método e validação da exceção
        Exception exception = assertThrows(ObjectNotFoundException.class, () ->
                consultaService.deletarConsulta(idConsulta)
        );

        assertEquals("Consulta não encontrado! ID: ", exception.getMessage());
        verify(consultaRepository, times(0)).deleteById(idConsulta);
    }

    @Test
    void deletarConsulta_ViolacaoDeIntegridade() {
        // Dados de entrada
        Long idConsulta = 1L;

        // Simulação do comportamento do repositório
        when(consultaRepository.findById(idConsulta)).thenReturn(Optional.of(new Consulta()));
        doThrow(new DataIntegrityViolationException("Data integrity violation")).when(consultaRepository).deleteById(idConsulta);

        // Execução do método e validação da exceção
        Exception exception = assertThrows(ExceptionDataIntegrityViolation.class, () ->
                consultaService.deletarConsulta(idConsulta)
        );

        assertEquals("Não é possível excluir, porque há entidades relacionadas.", exception.getMessage());
        verify(consultaRepository, times(1)).deleteById(idConsulta);
    }

    // Testes atualizar consulta: 2 cenários - se existe ou não
    @Test
    void atualizarConsulta_ConsultaExistente() {
        // Dados de entrada
        Long idConsulta = 1L;
        Consulta consultaAtualizada = new Consulta();
        consultaAtualizada.setIdConsulta(idConsulta);
        consultaAtualizada.setDescricao("Consulta médica atualizada");

        Consulta consultaExistente = new Consulta();
        consultaExistente.setIdConsulta(idConsulta);
        consultaExistente.setDescricao("Consulta médica");

        // Simulação do repositório
        when(consultaRepository.findById(idConsulta)).thenReturn(Optional.of(consultaExistente));
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consultaAtualizada);

        // Execução do método
        Consulta resultado = consultaService.atualizarConsulta(consultaAtualizada);

        // Validação do teste
        assertNotNull(resultado);
        assertEquals(idConsulta, resultado.getIdConsulta());
        assertEquals("Consulta médica atualizada", resultado.getDescricao());
        verify(consultaRepository, times(1)).save(consultaExistente);
    }

    @Test
    void atualizarConsulta_ConsultaNaoExistente() {
        // Dados de entrada
        Long idConsulta = 1L;
        Consulta consultaAtualizada = new Consulta();
        consultaAtualizada.setIdConsulta(idConsulta);
        consultaAtualizada.setDescricao("Consulta médica atualizada");

        // Simulação do repositório
        when(consultaRepository.findById(idConsulta)).thenReturn(Optional.empty());

        // Execução do método e validação da exceção
        Exception exception = assertThrows(ObjectNotFoundException.class, () ->
                consultaService.atualizarConsulta(consultaAtualizada)
        );

        assertEquals("Consulta não encontrado! ID: ", exception.getMessage());
        verify(consultaRepository, times(0)).save(any(Consulta.class));
    }

    @Test
    void updateData_AtualizarOsDadosCorretamente() {
        // Dados de entrada
        Consulta consultaExistente = new Consulta();
        consultaExistente.setIdConsulta(1L);
        consultaExistente.setDataConsulta(Date.from(LocalDate.now().atStartOfDay(ZoneId.of("GMT-3")).toInstant()));
        consultaExistente.setProfissional("Dr. Silva");
        consultaExistente.setEspecialidade("Cardiologia");

        Consulta consultaAtualizada = new Consulta();
        consultaAtualizada.setDataConsulta(Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.of("GMT-3")).toInstant()));
        consultaAtualizada.setProfissional("Dr. Souza");
        consultaAtualizada.setEspecialidade("Pediatria");

        // Invocação do método privado
        try {
            Method method = ConsultaService.class.getDeclaredMethod("updateData", Consulta.class, Consulta.class);
            method.setAccessible(true);
            method.invoke(consultaService, consultaExistente, consultaAtualizada);
        } catch (Exception e) {
            fail("Erro ao invocar método privado: " + e.getMessage());
        }

        // Validação dos dados atualizados
        LocalDate dataEsperada = consultaAtualizada.getDataConsulta().toInstant().atZone(ZoneId.of("GMT-3")).toLocalDate();
        LocalDate dataAtualizada = consultaExistente.getDataConsulta().toInstant().atZone(ZoneId.of("GMT-3")).toLocalDate();

        assertEquals(dataEsperada, dataAtualizada, "A data da consulta foi atualizada corretamente");
        assertEquals(consultaAtualizada.getProfissional(), consultaExistente.getProfissional());
        assertEquals(consultaAtualizada.getEspecialidade(), consultaExistente.getEspecialidade());
    }

}
