package com.agenda.dto;

import com.agenda.model.Contato;

public record ContatoCreateResponse(String nome) {
    public static ContatoCreateResponse fromEntity (Contato contato){
        return new ContatoCreateResponse(contato.getNome());
    }
}
