package com.agenda.service;

import com.agenda.model.ProfissionalDaSaude;
import com.agenda.repository.ProfissionalDaSaudeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfissionalDaSaudeService {

    ProfissionalDaSaudeRepository profissionalDaSaudeRepository;

    public ProfissionalDaSaudeService(ProfissionalDaSaudeRepository profissionalDaSaudeRepository){
        this.profissionalDaSaudeRepository = profissionalDaSaudeRepository;
    }

    public ProfissionalDaSaude create(ProfissionalDaSaude profissionalDaSaude){
        return profissionalDaSaudeRepository.save(profissionalDaSaude);
    }

    public List<ProfissionalDaSaude> listAll(){
        return profissionalDaSaudeRepository.findAllByOrderByNomeAsc();
    }
}
