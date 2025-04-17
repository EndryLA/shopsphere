package com.shopsphere.shopsphere.repositories;

import com.shopsphere.shopsphere.models.Specs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecsRepository extends JpaRepository<Specs,Integer> {

    public List<Specs> findSpecsByProductId(int id);

}
