package com.agenda.repository;

import com.agenda.model.ProfissionalDaSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProfissionalDaSaudeRepository extends JpaRepository<ProfissionalDaSaude, Long> {

    List<ProfissionalDaSaude> findAllByOrderByNomeAsc();

    List<ProfissionalDaSaude> findByNomeContainingIgnoreCase(String nome);
}
