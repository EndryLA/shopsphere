package com.shopsphere.shopsphere.repositories;

import com.shopsphere.shopsphere.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByEmail();

}
