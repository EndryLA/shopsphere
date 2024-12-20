package com.shopsphere.shopsphere.repositories;

import com.shopsphere.shopsphere.models.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserAdressRepository extends JpaRepository<UserAddress, Integer> {
}
