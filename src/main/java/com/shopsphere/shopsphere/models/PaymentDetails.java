package com.shopsphere.shopsphere.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "payment_details")
public class PaymentDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private double amount;

    @NotBlank
    private String provider;

    @NotBlank
    private String status;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private OrderDetails order;
}
