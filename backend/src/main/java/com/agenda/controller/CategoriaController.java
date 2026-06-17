package com.agenda.controller;

import com.agenda.dto.CategoriaDto;
import com.agenda.model.Categoria;
import com.agenda.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<CategoriaDto> criar(@Valid @RequestBody CategoriaDto dados) {
        Categoria categoria = categoriaService.create(dados.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoriaDto.fromEntity(categoria));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDto>> listar() {
        List<CategoriaDto> categorias = categoriaService.listAll()
                .stream()
                .map(CategoriaDto::fromEntity)
                .toList();

        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDto> buscar(@PathVariable Long id) {
        Categoria categoria = categoriaService.get(id);
        return ResponseEntity.ok(CategoriaDto.fromEntity(categoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaDto dados
    ) {
        Categoria categoria = categoriaService.update(id, dados.toEntity());
        return ResponseEntity.ok(CategoriaDto.fromEntity(categoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}