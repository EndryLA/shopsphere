package com.shopsphere.shopsphere.models;

import jakarta.persistence.*;

@Entity
@Table(name = "user_address")
public class UserAddress {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int street;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private int postal_code;

    @Column(nullable = false)
    private String country;

    @Column
    private String phone_number;
}
