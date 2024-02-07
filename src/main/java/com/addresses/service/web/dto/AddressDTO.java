package com.addresses.service.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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
    private Long userId;
    @Getter @Setter
    private Boolean defaultAddress;
    @Getter @Setter
    private Date deliveryDate;

}
