package com.shopsphere.shopsphere.services;

import com.shopsphere.shopsphere.models.OrderItem;
import com.shopsphere.shopsphere.repositories.OrderItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    public OrderItem getOrderItemById(int orderItemId) {
        return orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new EntityNotFoundException("Elément introuvable"));
    }

    public List<OrderItem> getOrderItemsByProductId(int productId) {
        return orderItemRepository.findByProductId(productId);
    }

}
