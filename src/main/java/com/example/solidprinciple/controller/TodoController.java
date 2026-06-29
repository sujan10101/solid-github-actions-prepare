package com.example.solidprinciple.controller;

import com.example.solidprinciple.dto.TodoRequest;
import com.example.solidprinciple.model.Todo;
import com.example.solidprinciple.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    // GET /api/todos
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    // GET /api/todos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.getTodoById(id));
    }

    // POST /api/todos
    @PostMapping
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody TodoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createTodo(request));
    }

    // PUT /api/todos/{id} — full update (send all fields)
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @Valid @RequestBody TodoRequest request) {
        return ResponseEntity.ok(todoService.updateTodo(id, request));
    }

    // PATCH /api/todos/{id} — partial update (only description)
    @PatchMapping("/{id}")
    public ResponseEntity<Todo> patchTodo(@PathVariable Long id, @RequestBody TodoRequest request) {
        return ResponseEntity.ok(todoService.patchTodo(id, request));
    }

    // DELETE /api/todos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}
