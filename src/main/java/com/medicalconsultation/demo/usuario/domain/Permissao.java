package com.medicalconsultation.demo.usuario.domain;

public enum Permissao {
    ADMIN("ADMIN"),

    SECRETARIO ("SECRETARIO"),

    PACIENTE ("PACIENTE");

    private final String descricao;


    Permissao(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao (){
        return descricao;
    }
}
