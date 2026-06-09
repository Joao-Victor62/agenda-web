package com.agenda.dto;

import com.agenda.model.Categoria;

public record CategoriaDto(String categoria) {
    public Categoria toEntity(){
        return new Categoria(categoria);
    }
}
