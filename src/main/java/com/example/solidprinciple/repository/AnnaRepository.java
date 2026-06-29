package com.example.solidprinciple.repository;

import com.example.solidprinciple.model.Anna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnaRepository extends JpaRepository<Anna, Long> {
}
