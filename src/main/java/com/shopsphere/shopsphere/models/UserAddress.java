package com.shopsphere.shopsphere.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "user_address")
public class UserAddress {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Veuillez saisir une adresse")
    private int street;

    @NotBlank(message = "Veuillez saisir une ville")
    private String city;

    @NotBlank(message = "Veuillez saisir un code postal")
    private int postal_code;

    @NotBlank(message = "Veuillez saisir un pays")
    private String country;

    @Min(value = 10, message = "Veuillez saisir un numéro de téléphone valide")
    private String phone_number;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStreet() {
        return street;
    }

    public void setStreet(int street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostalCode() {
        return postal_code;
    }

    public void setPostalCode(int postal_code) {
        this.postal_code = postal_code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

}
