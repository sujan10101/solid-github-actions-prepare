package com.example.solidprinciple.service;

import com.example.solidprinciple.dto.TodoRequest;
import com.example.solidprinciple.exception.TodoNotFoundException;
import com.example.solidprinciple.model.Todo;
import com.example.solidprinciple.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    // GET all
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    // GET one by id
    public Todo getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
    }

    // POST — create new
    public Todo createTodo(TodoRequest request) {
        Todo todo = Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .completed(request.isCompleted())
                .build();
        return todoRepository.save(todo);
    }

    // PUT — full update (replace all fields)
    public Todo updateTodo(Long id, TodoRequest request) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));

        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.isCompleted());

        return todoRepository.save(todo);
    }

    // PATCH — partial update (only updates description, everything else stays the same)
    public Todo patchTodo(Long id, TodoRequest request) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));

        if (request.getDescription() != null) {
            todo.setDescription(request.getDescription());
        }

        return todoRepository.save(todo);
    }

    // DELETE
    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new TodoNotFoundException(id);
        }
        todoRepository.deleteById(id);
    }
}
