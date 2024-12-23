package com.shopsphere.shopsphere.repositories;

import com.shopsphere.shopsphere.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {

    public List<OrderDetails> findByUserId(int userId);

}
