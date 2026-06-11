package com.agenda.repository;

import com.agenda.model.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CompromissoRepository extends JpaRepository<Atendimento, Long> {

    List<Atendimento> findAllByOrderByDataAscHoraAsc();

    List<Atendimento> findByDataOrderByHoraAsc(LocalDate data);

    List<Atendimento> findByContatoId(Long contatoId);
}
