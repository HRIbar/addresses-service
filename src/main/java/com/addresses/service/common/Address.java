package com.addresses.service.common;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "addresses") // Specifies the table name explicitly
public class Address {
    // Constructors, Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String streetNumber;
    @Getter @Setter
    private String street;
    @Getter @Setter
    private Integer postalCode;
    @Getter @Setter
    private String postOffice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // This column is the foreign key
    @Getter @Setter
    private User user;

    public Address() {
        // Default constructor
    }

    // Constructors, getters, and setters
}
