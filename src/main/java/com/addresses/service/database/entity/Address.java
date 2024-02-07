package com.addresses.service.database.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Calendar;
import java.util.Date;


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
    @Getter @Setter
    private Boolean defaultAddress;
    @Getter @Setter
    private Date deliveryDate;

    @Transient // This field is not persisted
    @Getter @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // This column is the foreign key
    @Getter @Setter
    private User user;

    public Address() {
    }

    public void populateUserId() {
        if (this.user != null) {
            this.userId = this.user.getId();
        }
    }

    public void initializeDeliveryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        this.deliveryDate = calendar.getTime();
    }

    // Constructors, getters, and setters
}
