package com.agenda.dto;

import com.agenda.model.ProfissionalDaSaude;

import java.time.LocalDateTime;

public record ContatoCreateRequest(
        String nome,
        String email,
        String telefone,
        String endereco,
        CategoriaDto categoria
){
    public ProfissionalDaSaude toEntity(){
        return new ProfissionalDaSaude(null, this.nome, this.telefone, this.email, this.endereco, this.categoria.toEntity(), LocalDateTime.now());
    }
}
