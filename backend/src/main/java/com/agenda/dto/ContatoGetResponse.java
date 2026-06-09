package com.agenda.dto;

import com.agenda.model.Categoria;
import com.agenda.model.Contato;

public record ContatoGetResponse(String nome,
                                 String telefone,
                                 String email,
                                 String endereco,
                                 CategoriaDto Categoria) {

    public static ContatoGetResponse fromEntity(Contato contato){
        return new ContatoGetResponse(contato.getNome(),
                contato.getTelefone(),
                contato.getEmail(),
                contato.getEndereco(),
                new CategoriaDto(contato.getCategoria().toString()));
    }
}
