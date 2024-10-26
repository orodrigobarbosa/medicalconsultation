package com.medicalconsultation.demo.usuario.services;

import com.medicalconsultation.demo.usuario.domain.Usuario;
import com.medicalconsultation.demo.usuario.repository.UsuarioRepository;
import jakarta.inject.Inject;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.postgresql.hostchooser.HostRequirement.any;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock //mascara o acesso ao repositório /simula acesso ao banco de dados
    private UsuarioRepository usuarioRepository;


    //teste de cadastrar usuario
    @Test
    void cadastrarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNomeUsuario("Rodrigo");

        //obedece a seguinte regra:
        // configuração do comportamento do MOCK
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);


        //A execução do método a ser testado
        var resultado = usuarioService.cadastrarUsuario(usuario);

        //Validação do Teste
        assertNotNull(usuario);
        assertEquals("Barbosa", resultado.getNomeUsuario());

        verify(usuarioRepository, times(1)).save(usuario);

    }


    //teste Listar Usuarios
    @Test
    void listarUsuarios(){

        Usuario usuario1 = new Usuario();
        usuario1.setNomeUsuario("Maria");

        Usuario usuario2 = new Usuario();
        usuario2.setNomeUsuario("João");

        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario1);
        usuarios.add(usuario2);

        //config do comportamento do MOCK
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        //A execucao do metodo a ser testado
       var resultado = usuarioService.listarUsuarios();

        //Validacao do Teste

        assertNotNull(resultado);
        assertEquals(2,resultado.size());
        assertEquals("Maria", resultado.get(0).getNomeUsuario());
        assertEquals("Joãoo", resultado.get(1).getNomeUsuario(), "O nome do usuario 2 está incorreto");
    }

    //teste buscar usuario por id: 2 Cenários (Precisa verificar se já existe ou nao)

    @Test
    void buscarUsuarioPorId_UsuarioExistente() {
        // Dados de entrada
        Long idUsuario = 1L;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        usuario.setNomeUsuario("Rodrigo");

        // Configuração do comportamento do mock
        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));

        // Execução do método a ser testado
        Usuario resultado = usuarioService.buscarUsuarioPorId(idUsuario);

        // Validação do teste
        assertNotNull(resultado);
        assertEquals("Rodrigo", resultado.getNomeUsuario());
        assertEquals(idUsuario, resultado.getIdUsuario());
        verify(usuarioRepository, times(1)).findById(idUsuario);
    }

    @Test
    void buscarUsuarioPorId_UsuarioNaoExistente() {
        // Dados de entrada
        Long idUsuario = 1L;

        // Configuração do comportamento do mock
        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.empty());

        // Execução do método a ser testado e validação da exceção
        Exception exception = assertThrows(ObjectNotFoundException.class, () ->
                usuarioService.buscarUsuarioPorId(idUsuario)
        );

        assertEquals("Usuario não encontrado pelo Id: ", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(idUsuario);
    }



    //teste de atualizar usuario: 2 Cenários
    //cenario 01
    @Test
    void atualizarUsuarioPorId_UsuarioExistente() {
        // Dados de entrada
        Long idUsuario = 1L;
        Usuario usuario = new Usuario();
        usuario.setNomeUsuario("Rodrigo");

        // Configuração do comportamento do mock
        when(usuarioRepository.existsById(idUsuario)).thenReturn(true);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Execução do método a ser testado
        Usuario resultado = usuarioService.atualizarUsuarioPorId(usuario, idUsuario);

        // Validação do teste
        assertNotNull(resultado);
        assertEquals("Rodrigo", resultado.getNomeUsuario());
        assertEquals(idUsuario, resultado.getIdUsuario());
        verify(usuarioRepository, times(1)).existsById(idUsuario);
        verify(usuarioRepository, times(1)).save(usuario);
    }
    //atualizar usuario cenario 02
    @Test
    void atualizarUsuarioPorId_UsuarioNaoExistente() {
        // Dados de entrada
        Long idUsuario = 1L;
        Usuario usuario = new Usuario();
        usuario.setNomeUsuario("Rodrigo");

        // Configuração do comportamento do mock
        when(usuarioRepository.existsById(idUsuario)).thenReturn(false);

        // Execução do método a ser testado e validação da exceção
        Exception exception = assertThrows(ObjectNotFoundException.class, () ->
                usuarioService.atualizarUsuarioPorId(usuario, idUsuario)
        );
//validacao do teste
        assertEquals("Usuário nao encontrado pelo id ", exception.getMessage());
        verify(usuarioRepository, times(1)).existsById(idUsuario);
        verify(usuarioRepository, times(0)).save(any(Usuario.class));
    }


    // teste deletar usuario
    @Test
    void deletarUsuarioPorId() {
        Long idUsuario = 1L;

        //nao se faz necessario uma config de comportamento mock, pois o método deletById já é void

        // Execução do método a ser testado
        usuarioService.deletarUsuarioPorId(idUsuario);

        // Validação do Teste
        verify(usuarioRepository, times(1)).deleteById(idUsuario);
    }


}