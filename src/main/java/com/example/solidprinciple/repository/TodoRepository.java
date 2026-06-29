package com.example.solidprinciple.repository;

import com.example.solidprinciple.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    // JpaRepository gives us save, findById, findAll, deleteById, existsById for free
}
