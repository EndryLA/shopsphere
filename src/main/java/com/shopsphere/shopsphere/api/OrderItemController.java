package com.shopsphere.shopsphere.api;

import com.shopsphere.shopsphere.domain.models.OrderItem;
import com.shopsphere.shopsphere.domain.services.OrderItemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/order_items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("order/{orderId}")
    public ResponseEntity<List<OrderItem>>  getOrderItemsByOrderId(@PathVariable int orderId) {

        try {
            List<OrderItem> orderItems = orderItemService.getOrderItemsByOrderId(orderId);
            return new ResponseEntity<>(orderItems, HttpStatus.OK);

        } catch(EntityNotFoundException exception) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND,exception.getMessage(),exception);

        } catch (Exception exception) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Une erreur inattendue est survenue",exception);

        }

    }

    @GetMapping("item/{orderItemId}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable int orderItemId) {
        try {
            OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);
            return new ResponseEntity<>(orderItem, HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Une erreur inattendue est survenue",exception);
        }

    }

}
