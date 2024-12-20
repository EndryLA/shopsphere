package com.shopsphere.shopsphere.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Veuillez saisir un nom de produit")
    private String name;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "Veuillez saisir une description")
    private String description;

    @Column(nullable = false)
    @NotBlank(message = "Veuillez saisir un prix")
    @Positive(message = "Le prix ne peut pas être inférieur à 0€")
    private double price;

    @OneToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id", nullable = false)
    @Valid
    private ProductCategory category;

    @OneToOne
    @JoinColumn(name = "quantity_id", referencedColumnName = "id", nullable = false)
    @Valid
    private ProductInventory quantity_id;

}
