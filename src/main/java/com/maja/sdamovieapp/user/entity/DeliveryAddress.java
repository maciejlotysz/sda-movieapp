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

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_default")
    private boolean isDefault;

    @NotBlank
    private  String street;

    @NotNull
    @Column(name = "building_number")
    private String buildingNumber;

    @Column(name = "appartement_number")
    private int appartementNumber;

    @NotBlank
    @Column(name = "zip_code")
    private String zipCode;

    @NotNull
    private String city;
}
