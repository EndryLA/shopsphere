package com.shopsphere.shopsphere.api;

import com.shopsphere.shopsphere.domain.models.OrderDetails;
import com.shopsphere.shopsphere.domain.services.OrderDetailsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/order_details")
public class OrderDetailsController {


    private final OrderDetailsService orderDetailsService;

    public OrderDetailsController(OrderDetailsService orderDetailsService) {
        this.orderDetailsService = orderDetailsService;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetails> getOrderDetailsByOrderId(@PathVariable int orderId) {

        try {

            OrderDetails orderDetails = orderDetailsService.getOrderDetailsById(orderId);
            return new ResponseEntity<>(orderDetails, HttpStatus.OK);

        } catch (EntityNotFoundException exception) {

            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        } catch (Exception exception) {
            throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
