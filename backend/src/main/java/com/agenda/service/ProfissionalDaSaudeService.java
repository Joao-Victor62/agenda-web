package com.agenda.service;

import com.agenda.model.Categoria;
import com.agenda.model.ProfissionalDaSaude;
import com.agenda.repository.ProfissionalDaSaudeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfissionalDaSaudeService {

    private final ProfissionalDaSaudeRepository profissionalDaSaudeRepository;
    private final CategoriaService categoriaService;

    public ProfissionalDaSaudeService(
            ProfissionalDaSaudeRepository profissionalDaSaudeRepository,
            CategoriaService categoriaService
    ) {
        this.profissionalDaSaudeRepository = profissionalDaSaudeRepository;
        this.categoriaService = categoriaService;
    }

    @Transactional
    public ProfissionalDaSaude create(ProfissionalDaSaude profissionalDaSaude) {
        Categoria categoria = categoriaService.findOrCreate(profissionalDaSaude.getCategoria());
        profissionalDaSaude.setCategoria(categoria);

        return profissionalDaSaudeRepository.save(profissionalDaSaude);
    }

    @Transactional(readOnly = true)
    public List<ProfissionalDaSaude> listAll() {
        return profissionalDaSaudeRepository.findAllByOrderByNomeAsc();
    }

    @Transactional(readOnly = true)
    public ProfissionalDaSaude get(Long id) {
        return profissionalDaSaudeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profissional da saúde não encontrado"));
    }

    @Transactional
    public ProfissionalDaSaude update(Long id, ProfissionalDaSaude dados) {
        ProfissionalDaSaude profissional = get(id);

        profissional.setNome(dados.getNome());
        profissional.setEmail(dados.getEmail());
        profissional.setTelefone(dados.getTelefone());
        profissional.setEndereco(dados.getEndereco());

        Categoria categoria = categoriaService.findOrCreate(dados.getCategoria());
        profissional.setCategoria(categoria);

        return profissionalDaSaudeRepository.save(profissional);
    }

    @Transactional
    public void delete(Long id) {
        ProfissionalDaSaude profissional = get(id);
        profissionalDaSaudeRepository.delete(profissional);
    }
}