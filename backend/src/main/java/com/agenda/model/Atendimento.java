package com.agenda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "atendimentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Data é obrigatória")
    private LocalDate data;

    @NotNull(message = "Hora é obrigatória")
    private LocalTime horario;

    @Column(columnDefinition = "TEXT")
    private String problema;

    @ManyToOne
    @JoinColumn(name = "receita_id")
    private Receita receita;
}
