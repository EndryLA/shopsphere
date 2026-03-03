package com.shopsphere.shopsphere.repositories;

import com.shopsphere.shopsphere.domain.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByEmail(String email);

    public Page<User> findAll(Pageable pageable);

}
