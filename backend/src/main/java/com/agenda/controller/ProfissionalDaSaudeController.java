package com.agenda.controller;

import com.agenda.dto.ContatoCreateResponse;
import com.agenda.dto.ContatoGetResponse;
import com.agenda.model.ProfissionalDaSaude;
import com.agenda.service.ProfissionalDaSaudeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contatos")
@CrossOrigin(origins = "*")
public class ProfissionalDaSaudeController {

    private final ProfissionalDaSaudeService profissionalDaSaudeService;

    public ProfissionalDaSaudeController(ProfissionalDaSaudeService profissionalDaSaudeService) {
        this.profissionalDaSaudeService = profissionalDaSaudeService;
    }

    // CREATE - Criar novo contato
    @PostMapping
    public ResponseEntity<ContatoCreateResponse> criar(@Valid @RequestBody ProfissionalDaSaude profissionalDaSaude) {
        ProfissionalDaSaude salvo = profissionalDaSaudeService.create(profissionalDaSaude);
        return ResponseEntity.status(HttpStatus.CREATED).body(ContatoCreateResponse.fromEntity(profissionalDaSaude));
    }

    // READ - Listar todos os contatos
    @GetMapping
    public ResponseEntity<List<ContatoGetResponse>> listar() {
        List<ProfissionalDaSaude> profissionalDaSaudes = profissionalDaSaudeService.listAll();
        return ResponseEntity.ok(profissionalDaSaudes.stream().map(ContatoGetResponse::fromEntity).toList());
    }

    // READ - Buscar contato por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        return profissionalDaSaudeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    // UPDATE - Atualizar contato
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @Valid @RequestBody ProfissionalDaSaude dados) {
        return profissionalDaSaudeService.findById(id)
                .map(contato -> {
                    contato.setNome(dados.getNome());
                    contato.setTelefone(dados.getTelefone());
                    contato.setEmail(dados.getEmail());
                    contato.setEndereco(dados.getEndereco());
                    return ResponseEntity.ok(profissionalDaSaudeService.save(contato));
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
