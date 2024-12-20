package com.shopsphere.shopsphere.repositories;

import com.shopsphere.shopsphere.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
