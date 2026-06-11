package com.agenda.dto;

import com.agenda.model.ProfissionalDaSaude;

public record ProfissionalDaSaudeResponse(String nome) {
    public static ProfissionalDaSaudeResponse fromEntity (ProfissionalDaSaude profissionalDaSaude){
        return new ProfissionalDaSaudeResponse(profissionalDaSaude.getNome());
    }
}
