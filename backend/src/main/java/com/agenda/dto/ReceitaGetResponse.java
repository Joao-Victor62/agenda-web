package com.agenda.dto;

import com.agenda.model.Receita;

public record ReceitaGetResponse(
        Long id,
        String receita
) {
    public static ReceitaGetResponse fromEntity(Receita receita) {
        return new ReceitaGetResponse(
                receita.getId(),
                receita.getReceita()
        );
    }
}