package com.agenda.dto;

import com.agenda.model.ProfissionalDaSaude;

public record ProfissionalDaSaudeGetResponse(String nome,
                                             String telefone,
                                             String email,
                                             String endereco,
                                             CategoriaDto Categoria) {

    public static ProfissionalDaSaudeGetResponse fromEntity(ProfissionalDaSaude profissionalDaSaude){
        return new ProfissionalDaSaudeGetResponse(profissionalDaSaude.getNome(),
                profissionalDaSaude.getTelefone(),
                profissionalDaSaude.getEmail(),
                profissionalDaSaude.getEndereco(),
                new CategoriaDto(profissionalDaSaude.getCategoria().toString()));
    }
}
