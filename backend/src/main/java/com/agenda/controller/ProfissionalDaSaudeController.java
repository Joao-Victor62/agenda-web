package com.agenda.controller;

import com.agenda.dto.ProfissionalDaSaudeGetResponse;
import com.agenda.dto.ProfissionalDaSaudeRequest;
import com.agenda.model.ProfissionalDaSaude;
import com.agenda.service.ProfissionalDaSaudeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contatos")
@CrossOrigin(origins = "*")
public class ProfissionalDaSaudeController {

    private final ProfissionalDaSaudeService profissionalDaSaudeService;

    public ProfissionalDaSaudeController(ProfissionalDaSaudeService profissionalDaSaudeService) {
        this.profissionalDaSaudeService = profissionalDaSaudeService;
    }

    @PostMapping
    public ResponseEntity<ProfissionalDaSaudeGetResponse> criar(
            @Valid @RequestBody ProfissionalDaSaudeRequest dados
    ) {
        ProfissionalDaSaude profissionalDaSaude = dados.toEntity();
        ProfissionalDaSaude salvo = profissionalDaSaudeService.create(profissionalDaSaude);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProfissionalDaSaudeGetResponse.fromEntity(salvo));
    }

    @GetMapping
    public ResponseEntity<List<ProfissionalDaSaudeGetResponse>> listar() {
        List<ProfissionalDaSaudeGetResponse> profissionais = profissionalDaSaudeService.listAll()
                .stream()
                .map(ProfissionalDaSaudeGetResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(profissionais);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalDaSaudeGetResponse> buscar(@PathVariable Long id) {
        ProfissionalDaSaude profissionalDaSaude = profissionalDaSaudeService.get(id);
        return ResponseEntity.ok(ProfissionalDaSaudeGetResponse.fromEntity(profissionalDaSaude));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalDaSaudeGetResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProfissionalDaSaudeRequest dados
    ) {
        ProfissionalDaSaude profissionalDaSaude = dados.toEntity();
        ProfissionalDaSaude atualizado = profissionalDaSaudeService.update(id, profissionalDaSaude);

        return ResponseEntity.ok(ProfissionalDaSaudeGetResponse.fromEntity(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        profissionalDaSaudeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}