package com.medicalconsultation.demo.usuario.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "USUARIOS")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private long idUsuario;

    @Column(name = "NOMEUSUARIO")
    private String nomeUsuario;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "CPF")
    private String cpf;
    @Column(name = "TELEFONE")
    private String telefone;
    @Column(name = "DATANASCIMENTO")
    private Date dataNascimento;

   private Permissao permissao;


    public Usuario() {
    }

    public Usuario(long idUsuario, String nomeUsuario, String email, String cpf, String telefone, Date dataNascimento) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.cpf = cpf;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
    }
}



