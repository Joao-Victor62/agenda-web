package com.agenda.dto;

import com.agenda.model.Atendimento;
import com.agenda.model.Receita;

import java.time.LocalDate;
import java.time.LocalTime;

public record AtendimentoRequest(
        LocalDate data,
        LocalTime horario,
        String problema,
        ReceitaDto receita
) {
    public Atendimento toEntity() {
        Receita receitaEntity = this.receita != null
                ? this.receita.toEntity()
                : null;

        return new Atendimento(
                null,
                this.data,
                this.horario,
                this.problema,
                receitaEntity
        );
    }
}