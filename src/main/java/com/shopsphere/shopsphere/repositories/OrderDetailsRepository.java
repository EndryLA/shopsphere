package com.shopsphere.shopsphere.repositories;

import com.shopsphere.shopsphere.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
}
