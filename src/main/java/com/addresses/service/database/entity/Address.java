package com.addresses.service.database.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses") // Specifies the table name explicitly
public class Address {
    // Constructors, Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    private Long id;
    @Setter
    private String streetNumber;
    @Setter
    private String street;
    @Setter
    private Integer postalCode;
    @Setter
    private String postOffice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // This column is the foreign key
    @Setter
    private User user;

    public Address() {
        // Default constructor
    }

    // Constructors, getters, and setters
}
