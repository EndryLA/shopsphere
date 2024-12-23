package com.shopsphere.shopsphere.repositories;

import com.shopsphere.shopsphere.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    public List<OrderItem> findByOrderId(int orderId);

    public List<OrderItem> findByProductId(int productId);

}
