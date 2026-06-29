package com.example.solidprinciple.controller;

import com.example.solidprinciple.dto.AnnaRequest;
import com.example.solidprinciple.model.Anna;
import com.example.solidprinciple.service.AnnaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anna")
@RequiredArgsConstructor
public class AnnaController {

    private final AnnaService annaService;

    @GetMapping
    public ResponseEntity<List<Anna>> getAll() {
        return ResponseEntity.ok(annaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anna> getById(@PathVariable Long id) {
        return ResponseEntity.ok(annaService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Anna> create(@Valid @RequestBody AnnaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(annaService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Anna> update(@PathVariable Long id, @Valid @RequestBody AnnaRequest request) {
        return ResponseEntity.ok(annaService.update(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Anna> patch(@PathVariable Long id, @RequestBody AnnaRequest request) {
        return ResponseEntity.ok(annaService.patch(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        annaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
