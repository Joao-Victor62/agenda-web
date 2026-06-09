package com.agenda.service;

import com.agenda.model.Contato;
import com.agenda.repository.ContatoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContatoService {

    ContatoRepository contatoRepository;

    public ContatoService(ContatoRepository contatoRepository){
        this.contatoRepository = contatoRepository;
    }

    public Contato create(Contato contato){
        return contatoRepository.save(contato);
    }

    public List<Contato> listAll(){
        return contatoRepository.findAllByOrderByNomeAsc();
    }
}
