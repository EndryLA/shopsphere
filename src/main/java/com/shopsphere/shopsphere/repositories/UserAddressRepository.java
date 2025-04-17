package com.shopsphere.shopsphere.repositories;

import com.shopsphere.shopsphere.domain.models.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository extends JpaRepository<UserAddress, Integer> {

}
