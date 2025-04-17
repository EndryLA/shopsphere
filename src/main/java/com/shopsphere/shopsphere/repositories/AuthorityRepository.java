package com.shopsphere.shopsphere.repositories;

import com.shopsphere.shopsphere.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

}
