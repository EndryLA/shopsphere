package com.shopsphere.shopsphere.models;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private double price;

    @OneToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id", nullable = false)
    private ProductCategory category;

    @OneToOne
    @JoinColumn(name = "quantity_id", referencedColumnName = "id", nullable = false)
    private ProductInventory quantity_id;

}
