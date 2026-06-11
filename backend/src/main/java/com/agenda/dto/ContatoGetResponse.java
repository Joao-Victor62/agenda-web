package com.agenda.dto;

import com.agenda.model.ProfissionalDaSaude;

public record ContatoGetResponse(String nome,
                                 String telefone,
                                 String email,
                                 String endereco,
                                 CategoriaDto Categoria) {

    public static ContatoGetResponse fromEntity(ProfissionalDaSaude profissionalDaSaude){
        return new ContatoGetResponse(profissionalDaSaude.getNome(),
                profissionalDaSaude.getTelefone(),
                profissionalDaSaude.getEmail(),
                profissionalDaSaude.getEndereco(),
                new CategoriaDto(profissionalDaSaude.getCategoria().toString()));
    }
}
