package com.shopsphere.shopsphere.domain.services;

import com.shopsphere.shopsphere.domain.models.OrderDetails;
import com.shopsphere.shopsphere.repositories.OrderDetailsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;

    public OrderDetailsService(OrderDetailsRepository orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
    }

    public List<OrderDetails> getOrderDetailsByUserId(int userId) {
        return orderDetailsRepository.findByUserId(userId);
    }

    public OrderDetails getOrderDetailsById(int id) {
        return orderDetailsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Commande introuvable"));
    }

    public OrderDetails createOrderDetails(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }
}
