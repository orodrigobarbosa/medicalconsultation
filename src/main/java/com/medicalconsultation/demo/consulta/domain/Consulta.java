package com.medicalconsultation.demo.consulta.domain;

import com.medicalconsultation.demo.usuario.domain.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
@Entity
@Table(name = "CONSULTAS")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONSULTA")
    private Long idConsulta;

    @Column(name= "DATA_CONSULTA")
    private Date dataConsulta;

    @Column(name= "PROFISSIONAL")
    private String profissional;

    @Column(name= "ESPECIALIDADE")
    private String especialidade;

    @Column(name= "DESCRICAO")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
}
