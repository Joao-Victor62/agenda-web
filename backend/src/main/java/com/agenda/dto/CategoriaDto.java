package com.agenda.dto;

import com.agenda.model.Categoria;

public record CategoriaDto(
        Long id,
        String categoria
) {
    public CategoriaDto(String categoria) {
        this(null, categoria);
    }

    public Categoria toEntity() {
        return new Categoria(id, categoria);
    }

    public static CategoriaDto fromEntity(Categoria categoria) {
        if (categoria == null) {
            return null;
        }

        return new CategoriaDto(
                categoria.getId(),
                categoria.getCategoria()
        );
    }
}