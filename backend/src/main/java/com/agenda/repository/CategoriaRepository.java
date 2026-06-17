package com.agenda.repository;

import com.agenda.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findAllByOrderByCategoriaAsc();

    Optional<Categoria> findByCategoriaIgnoreCase(String categoria);

    boolean existsByCategoriaIgnoreCase(String categoria);
}