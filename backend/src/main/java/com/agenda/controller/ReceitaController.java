package com.agenda.controller;

import com.agenda.model.Receita;
import com.agenda.service.ReceitaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/receitas")
@CrossOrigin(origins = "*")
public class ReceitaController {

    private final ReceitaService service;

    public ReceitaController(ReceitaService service) {
        this.service = service;
    }

    // CREATE - Criar nova receita
    @PostMapping
    public ResponseEntity<Receita> criar(@Valid @RequestBody Receita receita) {
        Receita salva = service.criar(receita);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    // READ - Listar todas as receitas
    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    // READ - Buscar receita por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // READ - Buscar receita por texto
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorTexto(@RequestParam String texto) {
        return ResponseEntity.ok(service.buscarPorTexto(texto));
    }

    // UPDATE - Atualizar receita
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @Valid @RequestBody Receita dados) {
        return service.atualizar(id, dados)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE - Remover receita
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        boolean deletado = service.deletar(id);

        if (!deletado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                Map.of("mensagem", "Receita removida com sucesso")
        );
    }
}