package com.medicalconsultation.demo.usuario.services;

import com.medicalconsultation.demo.usuario.domain.Usuario;
import com.medicalconsultation.demo.usuario.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public Usuario cadastrarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }




}
