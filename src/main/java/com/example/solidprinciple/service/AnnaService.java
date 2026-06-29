package com.example.solidprinciple.service;

import com.example.solidprinciple.dto.AnnaRequest;
import com.example.solidprinciple.model.Anna;
import com.example.solidprinciple.repository.AnnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnaService {

    private final AnnaRepository annaRepository;

    public List<Anna> getAll() {
        return annaRepository.findAll();
    }

    public Anna getById(Long id) {
        return annaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anna with id " + id + " not found"));
    }

    public Anna create(AnnaRequest request) {
        Anna anna = Anna.builder()
                .name(request.getName())
                .country(request.getCountry())
                .build();
        return annaRepository.save(anna);
    }

    public Anna update(Long id, AnnaRequest request) {
        Anna anna = getById(id);
        anna.setName(request.getName());
        anna.setCountry(request.getCountry());
        return annaRepository.save(anna);
    }

    public Anna patch(Long id, AnnaRequest request) {
        Anna anna = getById(id);
        if (request.getCountry() != null) {
            anna.setCountry(request.getCountry());
        }
        return annaRepository.save(anna);
    }

    public void delete(Long id) {
        if (!annaRepository.existsById(id)) {
            throw new RuntimeException("Anna with id " + id + " not found");
        }
        annaRepository.deleteById(id);
    }
}
