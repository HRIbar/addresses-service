package com.addresses.service.database.repository;

import com.addresses.service.database.entity.Address;
import com.addresses.service.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUser_Id(Long userId);

    long countByUser_Id(Long userId);

    List<Address> findByUserAndStreetNumberAndStreetAndPostalCodeAndPostOffice(
            User user, String streetNumber, String street, Integer postalCode, String postOffice);
}
