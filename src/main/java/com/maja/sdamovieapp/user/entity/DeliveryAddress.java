package com.maja.sdamovieapp.user.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "delivery_addresses")
public class DeliveryAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    private  String street;

    @NotBlank
    @Column(name = "building_number")
    private int buildingNumber;

    @Column(name = "appartement_number")
    private int appartementNumber;

    @NotNull
    @Column(name = "zip_code")
    private String zipCode;

    @NotNull
    private String city;
}
