package com.medicalconsultation.demo.usuario.services;

import com.medicalconsultation.demo.usuario.domain.Usuario;
import com.medicalconsultation.demo.usuario.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public Usuario cadastrarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado pelo Id: ", id));
    }

    public Usuario atualizarUsuarioPorId(Usuario usuario, Long id) {
        if (usuarioRepository.existsById(id)) {
            usuario.setIdUsuario(id);
            return usuarioRepository.save(usuario);

        } else {
            throw new ObjectNotFoundException("Usuário nao encontrado pelo id ", id);
        }
    }

    public void  deletarUsuarioPorId(Long id){
        usuarioRepository.deleteById(id);
    }
}
