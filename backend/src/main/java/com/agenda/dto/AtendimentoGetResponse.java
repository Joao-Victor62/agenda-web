package com.agenda.dto;

import com.agenda.model.Atendimento;

import java.time.LocalDate;
import java.time.LocalTime;

public record AtendimentoGetResponse(
        Long id,
        LocalDate data,
        LocalTime horario,
        String problema,
        ReceitaDto receita
) {
    public static AtendimentoGetResponse fromEntity(Atendimento atendimento) {
        return new AtendimentoGetResponse(
                atendimento.getId(),
                atendimento.getData(),
                atendimento.getHorario(),
                atendimento.getProblema(),
                ReceitaDto.fromEntity(atendimento.getReceita())
        );
    }
}