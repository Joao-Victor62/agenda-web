package com.agenda.controller;

import com.agenda.dto.ContatoCreateResponse;
import com.agenda.dto.ContatoGetResponse;
import com.agenda.model.Contato;
import com.agenda.service.ContatoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contatos")
@CrossOrigin(origins = "*")
public class ContatoController {

    private final ContatoService contatoService;

    public ContatoController(ContatoService contatoService) {
        this.contatoService = contatoService;
    }

    // CREATE - Criar novo contato
    @PostMapping
    public ResponseEntity<ContatoCreateResponse> criar(@Valid @RequestBody Contato contato) {
        Contato salvo = contatoService.create(contato);
        return ResponseEntity.status(HttpStatus.CREATED).body(ContatoCreateResponse.fromEntity(contato));
    }

    // READ - Listar todos os contatos
    @GetMapping
    public ResponseEntity<List<ContatoGetResponse>> listar() {
        List<Contato> contatos = contatoService.listAll();
        return ResponseEntity.ok(contatos.stream().map(ContatoGetResponse::fromEntity).toList());
    }

    // READ - Buscar contato por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        return contatoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    // UPDATE - Atualizar contato
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @Valid @RequestBody Contato dados) {
        return contatoService.findById(id)
                .map(contato -> {
                    contato.setNome(dados.getNome());
                    contato.setTelefone(dados.getTelefone());
                    contato.setEmail(dados.getEmail());
                    contato.setEndereco(dados.getEndereco());
                    return ResponseEntity.ok(contatoService.save(contato));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE - Remover contato
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return repository.findById(id)
                .map(contato -> {
                    repository.delete(contato);
                    return ResponseEntity.ok(Map.of("mensagem", "Contato removido com sucesso"));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
