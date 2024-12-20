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
}
