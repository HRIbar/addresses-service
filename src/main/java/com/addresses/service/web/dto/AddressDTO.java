package com.addresses.service.web.dto;

import lombok.Getter;
import lombok.Setter;

public class AddressDTO {
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
    private Long userId; // Optional: Include if you want to show the User ID related to the Address
    // Constructors, getters, and setters
}
