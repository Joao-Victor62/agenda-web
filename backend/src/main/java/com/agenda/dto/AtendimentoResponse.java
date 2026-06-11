package com.agenda.dto;

import com.agenda.model.Atendimento;

import java.time.LocalDate;
import java.time.LocalTime;

public record AtendimentoResponse(
        LocalDate data,
        LocalTime horario,
        String problema
) {
    public static AtendimentoResponse fromEntity(Atendimento atendimento) {
        return new AtendimentoResponse(
                atendimento.getData(),
                atendimento.getHorario(),
                atendimento.getProblema()
        );
    }
}