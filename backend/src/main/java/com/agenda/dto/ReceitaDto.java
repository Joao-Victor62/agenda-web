package com.agenda.dto;

import com.agenda.model.Receita;

public record ReceitaDto(
        Long id,
        String receita
) {
    public Receita toEntity() {
        return new Receita(this.id, this.receita);
    }

    public static ReceitaDto fromEntity(Receita receita) {
        if (receita == null) {
            return null;
        }

        return new ReceitaDto(
                receita.getId(),
                receita.getReceita()
        );
    }
}