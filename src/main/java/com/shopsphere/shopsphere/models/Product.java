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

    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id", nullable = false)
    @Valid
    private ProductCategory category;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_id", referencedColumnName = "id", nullable = false)
    @Valid
    private ProductInventory inventory;

    private double averageRating;

    private String mainSpecs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public ProductInventory getInventory() {
        return inventory;
    }

    public void setInventory(ProductInventory inventory) {
        this.inventory = inventory;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public String getMainSpecs() {
        return mainSpecs;
    }

    public void setMainSpecs(String mainSpecs) {
        this.mainSpecs = mainSpecs;
    }
}
