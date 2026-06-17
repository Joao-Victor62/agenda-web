package com.agenda.dto;

import com.agenda.model.ProfissionalDaSaude;

public record ProfissionalDaSaudeGetResponse(
        Long id,
        String nome,
        String telefone,
        String email,
        String endereco,
        CategoriaDto Categoria
) {
    public static ProfissionalDaSaudeGetResponse fromEntity(ProfissionalDaSaude profissionalDaSaude) {
        return new ProfissionalDaSaudeGetResponse(
                profissionalDaSaude.getId(),
                profissionalDaSaude.getNome(),
                profissionalDaSaude.getTelefone(),
                profissionalDaSaude.getEmail(),
                profissionalDaSaude.getEndereco(),
                CategoriaDto.fromEntity(profissionalDaSaude.getCategoria())
        );
    }
}