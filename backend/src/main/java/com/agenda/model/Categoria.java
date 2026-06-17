package com.agenda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "categorias",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "categoria")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Categoria é obrigatória")
    @Column(nullable = false, unique = true, length = 100)
    private String categoria;

    public Categoria(String categoria) {
        this.categoria = categoria;
    }
}