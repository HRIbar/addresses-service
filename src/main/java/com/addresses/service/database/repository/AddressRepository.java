package com.addresses.service.database.repository;

import com.addresses.service.database.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    // Additional query methods can be defined here
}
