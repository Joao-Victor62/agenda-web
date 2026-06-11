package com.agenda.service;

import com.agenda.model.Receita;
import com.agenda.repository.ReceitaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReceitaService {

    private final ReceitaRepository repository;

    public ReceitaService(ReceitaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Receita criar(Receita receita) {
        return repository.save(receita);
    }

    public List<Receita> listarTodas() {
        return repository.findAll();
    }

    public Optional<Receita> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<Receita> buscarPorTexto(String texto) {
        return repository.findByReceitaContainingIgnoreCase(texto);
    }

    @Transactional
    public Optional<Receita> atualizar(Long id, Receita dados) {
        return repository.findById(id)
                .map(receita -> {
                    receita.setReceita(dados.getReceita());
                    return repository.save(receita);
                });
    }

    @Transactional
    public boolean deletar(Long id) {
        return repository.findById(id)
                .map(receita -> {
                    repository.delete(receita);
                    return true;
                })
                .orElse(false);
    }
}