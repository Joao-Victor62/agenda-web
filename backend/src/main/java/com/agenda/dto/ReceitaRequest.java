package com.agenda.dto;

import com.agenda.model.Receita;

public record ReceitaRequest(
        String receita
) {
    public Receita toEntity() {
        return new Receita(null, this.receita);
    }
}