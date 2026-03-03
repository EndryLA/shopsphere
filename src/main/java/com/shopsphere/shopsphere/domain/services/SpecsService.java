package com.shopsphere.shopsphere.domain.services;

import com.shopsphere.shopsphere.domain.models.Specs;
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

    public Specs getSpecById(int id) {
        return specsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Spécification introuvable"));
    }

    public Specs createSpec(Specs spec) {

        return specsRepository.save(spec);

    }

    public Specs updateSpec(Specs spec) {

        Specs specToUpdate = specsRepository.findById(spec.getId())
                .orElseThrow(() -> new EntityNotFoundException("Spécification introuvable"));

        specToUpdate.setKey(spec.getKey());
        specToUpdate.setValue(spec.getValue());

        return specsRepository.save(specToUpdate);
    }

    public void deleteSpec(int id) {
        specsRepository.deleteById(id);
    }



}
