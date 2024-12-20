package com.shopsphere.shopsphere.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Veuillez saisir un prénom")
    @Size(min = 2,message = "Le Prénom doit contenir au moins 2 caractères")
    private String firstname;

    @NotBlank(message = "Veuillez saisir un nom")
    @Size(min = 2,message = "Le nom doit contenir au moins 2 caractères")
    private String lastname;

    @Column(unique = true)
    @Email(message = "Veuillez saisir une adresse email valide")
    @NotBlank(message = "Veuillez saisir une adresse mail")
    private String email;

    @NotBlank
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    @Valid
    private UserAddress address;

}
