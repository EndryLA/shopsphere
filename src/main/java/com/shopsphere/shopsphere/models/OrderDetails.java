package com.shopsphere.shopsphere.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "order_details")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private Date date;

    @OneToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private PaymentDetails paymentDetails;

}
