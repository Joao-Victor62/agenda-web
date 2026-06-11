package com.agenda.dto;

import com.agenda.model.Receita;

public record ReceitaResponse(
        String receita
) {
    public static ReceitaResponse fromEntity(Receita receita) {
        return new ReceitaResponse(
                receita.getReceita()
        );
    }
}