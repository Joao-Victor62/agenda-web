package com.agenda.dto;

import com.agenda.model.Contato;

import java.time.LocalDateTime;

public record ContatoCreateRequest(
        String nome,
        String email,
        String telefone,
        String endereco,
        CategoriaDto categoria
){
    public Contato toEntity(){
        return new Contato(null, this.nome, this.telefone, this.email, this.endereco, this.categoria.toEntity(), LocalDateTime.now());
    }
}
