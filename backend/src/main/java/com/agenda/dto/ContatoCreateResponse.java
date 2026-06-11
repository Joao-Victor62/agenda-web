package com.agenda.dto;

import com.agenda.model.ProfissionalDaSaude;

public record ContatoCreateResponse(String nome) {
    public static ContatoCreateResponse fromEntity (ProfissionalDaSaude profissionalDaSaude){
        return new ContatoCreateResponse(profissionalDaSaude.getNome());
    }
}
