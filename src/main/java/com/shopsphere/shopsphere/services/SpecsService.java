package com.shopsphere.shopsphere.services;

import com.shopsphere.shopsphere.models.Specs;
import com.shopsphere.shopsphere.repositories.SpecsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecsService {

    private final SpecsRepository specsRepository;

    public SpecsService(SpecsRepository specsRepository) {
        this.specsRepository = specsRepository;
    }

    public List<Specs> getSpecsByProductId(int id) {
        return specsRepository.findSpecsByProductId(id);
    }

    public Specs getSpecsById(int id) {
        return specsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Spécification introuvable"));
    }



}
