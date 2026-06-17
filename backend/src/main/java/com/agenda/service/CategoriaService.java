package com.agenda.service;

import com.agenda.model.Categoria;
import com.agenda.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public Categoria create(Categoria categoria) {
        String nome = normalizarNome(categoria.getCategoria());

        return categoriaRepository.findByCategoriaIgnoreCase(nome)
                .orElseGet(() -> categoriaRepository.save(new Categoria(nome)));
    }

    @Transactional(readOnly = true)
    public List<Categoria> listAll() {
        return categoriaRepository.findAllByOrderByCategoriaAsc();
    }

    @Transactional(readOnly = true)
    public Categoria get(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }

    @Transactional
    public Categoria update(Long id, Categoria dados) {
        Categoria categoria = get(id);
        categoria.setCategoria(normalizarNome(dados.getCategoria()));
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void delete(Long id) {
        Categoria categoria = get(id);
        categoriaRepository.delete(categoria);
    }

    @Transactional
    public Categoria findOrCreate(Categoria categoria) {
        if (categoria == null || categoria.getCategoria() == null || categoria.getCategoria().isBlank()) {
            return null;
        }

        String nome = normalizarNome(categoria.getCategoria());

        return categoriaRepository.findByCategoriaIgnoreCase(nome)
                .orElseGet(() -> categoriaRepository.save(new Categoria(nome)));
    }

    private String normalizarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Categoria é obrigatória");
        }

        return nome.trim();
    }
}