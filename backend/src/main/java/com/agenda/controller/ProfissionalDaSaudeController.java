package com.agenda.controller;

import com.agenda.dto.ProfissionalDaSaudeRequest;
import com.agenda.dto.ProfissionalDaSaudeResponse;
import com.agenda.dto.ProfissionalDaSaudeGetResponse;
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
    public ResponseEntity<ProfissionalDaSaudeResponse> criar(@Valid @RequestBody ProfissionalDaSaude profissionalDaSaude) {
        ProfissionalDaSaude salvo = profissionalDaSaudeService.create(profissionalDaSaude);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProfissionalDaSaudeResponse.fromEntity(profissionalDaSaude));
    }

    // READ - Listar todos os contatos
    @GetMapping
    public ResponseEntity<List<ProfissionalDaSaudeGetResponse>> listar() {
        List<ProfissionalDaSaude> profissionalDaSaudes = profissionalDaSaudeService.listAll();
        return ResponseEntity.ok(profissionalDaSaudes.stream().map(ProfissionalDaSaudeGetResponse::fromEntity).toList());
    }

    // READ - Buscar contato por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalDaSaudeGetResponse> buscar(@PathVariable Long id) {
        ProfissionalDaSaudeGetResponse response = ProfissionalDaSaudeGetResponse.fromEntity(profissionalDaSaudeService.get(id));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // UPDATE - Atualizar contato
    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalDaSaudeGetResponse> atualizar(@PathVariable Long id,
                                       @Valid @RequestBody ProfissionalDaSaudeRequest dados) {
        ProfissionalDaSaude profissionalDaSaude = dados.toEntity();
        ProfissionalDaSaudeGetResponse response =  ProfissionalDaSaudeGetResponse.fromEntity(profissionalDaSaudeService.update(profissionalDaSaude));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // DELETE - Remover contato
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        profissionalDaSaudeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
