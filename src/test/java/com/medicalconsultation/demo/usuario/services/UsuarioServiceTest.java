package com.medicalconsultation.demo.usuario.services;

import com.medicalconsultation.demo.usuario.domain.Usuario;
import com.medicalconsultation.demo.usuario.repository.UsuarioRepository;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void  cadastrarUsuario(){
     Usuario usuario = new Usuario();
     usuario.setNomeUsuario("Rodrigo");

     //obedece a seguinte regra:
    // configuração do comportamento do MOCK
     when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);


    //A execução do método a ser testado
    var resaltado = usuarioService.cadastrarUsuario(usuario);

    //Validação do Teste
        assertNotNull(usuario);
        assertEquals("Barbosa", resaltado.getNomeUsuario());

        verify(usuarioRepository, times(1)).save(usuario);

}}