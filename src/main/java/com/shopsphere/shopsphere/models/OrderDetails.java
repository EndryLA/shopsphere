package com.shopsphere.shopsphere.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

@Entity
@Table(name = "order_details")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private double amount;

    @NotBlank(message = "La date de la commande est érronée")
    private Date date;

    @OneToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    @Valid
    private PaymentDetails paymentDetails;

}
