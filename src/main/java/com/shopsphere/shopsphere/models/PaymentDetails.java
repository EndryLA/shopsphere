package com.shopsphere.shopsphere.models;

import jakarta.persistence.*;

@Entity
@Table(name = "payment_details")
public class PaymentDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private String status;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private OrderDetails order;
}
