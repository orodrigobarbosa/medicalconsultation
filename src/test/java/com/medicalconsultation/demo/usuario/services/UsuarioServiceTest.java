package com.medicalconsultation.demo.usuario.services;

import com.medicalconsultation.demo.usuario.domain.Usuario;
import com.medicalconsultation.demo.usuario.repository.UsuarioRepository;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

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


}