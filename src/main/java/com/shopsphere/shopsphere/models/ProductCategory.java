package com.shopsphere.shopsphere.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "product_category")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Veuillez saisir un nom pour le produit")
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Veuillez saisir une description valide")
    private String description;

}
