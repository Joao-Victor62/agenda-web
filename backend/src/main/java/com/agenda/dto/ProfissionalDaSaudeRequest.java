package com.agenda.dto;

import com.agenda.model.Categoria;
import com.agenda.model.ProfissionalDaSaude;

import java.time.LocalDateTime;

public record ProfissionalDaSaudeRequest(
        String nome,
        String email,
        String telefone,
        String endereco,
        CategoriaDto categoria
) {
    public ProfissionalDaSaude toEntity() {
        Categoria categoriaEntity = this.categoria == null ? null : this.categoria.toEntity();

        return new ProfissionalDaSaude(
                null,
                this.nome,
                this.telefone,
                this.email,
                this.endereco,
                categoriaEntity,
                LocalDateTime.now()
        );
    }
}