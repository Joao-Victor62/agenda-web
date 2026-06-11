package com.agenda.service;

import com.agenda.model.Atendimento;
import com.agenda.repository.AtendimentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AtendimentoService {

    private final AtendimentoRepository repository;

    public AtendimentoService(AtendimentoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Atendimento criar(Atendimento atendimento) {
        return repository.save(atendimento);
    }

    public List<Atendimento> listarTodos() {
        return repository.findAllByOrderByDataAscHorarioAsc();
    }

    public Optional<Atendimento> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<Atendimento> buscarPorData(LocalDate data) {
        return repository.findByDataOrderByHorarioAsc(data);
    }

    public List<Atendimento> buscarPorReceitaId(Long receitaId) {
        return repository.findByReceitaId(receitaId);
    }

    @Transactional
    public Optional<Atendimento> atualizar(Long id, Atendimento dados) {
        return repository.findById(id)
                .map(atendimento -> {
                    atendimento.setData(dados.getData());
                    atendimento.setHorario(dados.getHorario());
                    atendimento.setProblema(dados.getProblema());
                    atendimento.setReceita(dados.getReceita());

                    return repository.save(atendimento);
                });
    }

    @Transactional
    public boolean deletar(Long id) {
        return repository.findById(id)
                .map(atendimento -> {
                    repository.delete(atendimento);
                    return true;
                })
                .orElse(false);
    }
}