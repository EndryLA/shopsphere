package com.shopsphere.shopsphere.repositories;

import com.shopsphere.shopsphere.domain.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    public Authority findByRole(String role);

}
