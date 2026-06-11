package com.agenda.controller;

import com.agenda.model.Atendimento;
import com.agenda.repository.AtendimentoRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/atendimentos")
@CrossOrigin(origins = "*")
public class AtendimentoController {

    private final AtendimentoRepository repository;

    public AtendimentoController(AtendimentoRepository repository) {
        this.repository = repository;
    }

    // CREATE - Criar novo atendimento
    @PostMapping
    public ResponseEntity<Atendimento> criar(@Valid @RequestBody Atendimento atendimento) {
        Atendimento salvo = repository.save(atendimento);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // READ - Listar todos os atendimentos
    @GetMapping
    public ResponseEntity<List<Atendimento>> listar() {
        List<Atendimento> atendimentos = repository.findAllByOrderByDataAscHorarioAsc();
        return ResponseEntity.ok(atendimentos);
    }

    // READ - Buscar atendimento por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // UPDATE - Atualizar atendimento
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @Valid @RequestBody Atendimento dados) {
        return repository.findById(id)
                .<ResponseEntity<?>>map(atendimento -> {
                    atendimento.setData(dados.getData());
                    atendimento.setHorario(dados.getHorario());
                    atendimento.setProblema(dados.getProblema());
                    atendimento.setReceita(dados.getReceita());

                    Atendimento atualizado = repository.save(atendimento);
                    return ResponseEntity.ok(atualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE - Remover atendimento
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return repository.findById(id)
                .<ResponseEntity<?>>map(atendimento -> {
                    repository.delete(atendimento);
                    return ResponseEntity.ok(
                            Map.of("mensagem", "Atendimento removido com sucesso")
                    );
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}