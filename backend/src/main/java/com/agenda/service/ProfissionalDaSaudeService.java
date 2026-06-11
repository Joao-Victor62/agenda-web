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

    public ProfissionalDaSaude update(ProfissionalDaSaude profissionalDaSaude){
        ProfissionalDaSaude dbData = this.get(profissionalDaSaude.getId());
        dbData.setNome(profissionalDaSaude.getNome());
        dbData.setEmail(profissionalDaSaude.getEmail());
        dbData.setTelefone(profissionalDaSaude.getTelefone());
        dbData.setCategoria(profissionalDaSaude.getCategoria());
        dbData.setEndereco(profissionalDaSaude.getEndereco());
        return dbData;
    }


    public ProfissionalDaSaude get(Long id){return profissionalDaSaudeRepository.findById(id).orElseThrow(() -> new RuntimeException("ERRO 404: Objeto não encontrado"));}

    public void delete(Long id){
        if (this.get(id) != null)
            profissionalDaSaudeRepository.deleteById(id);
    }
}
