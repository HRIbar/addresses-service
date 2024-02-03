package com.addresses.service.web;

import com.addresses.service.database.entity.Address;
import com.addresses.service.database.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses-service/address")
public class AddressAPIController {

    private final AddressService addressService;

    @Autowired
    public AddressAPIController(AddressService addressService) {
        this.addressService = addressService;
    }

    // Retrieve address by user's ID
    @GetMapping("/{userId}")
    public ResponseEntity<?> getAddressesByUserId(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(addressService.findAddressesByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not find addresses for user with ID: " + userId);
        }
    }

    // Store address
    @PostMapping
    public ResponseEntity<?> saveAddress(@RequestBody Address address) {
        try {
            Address savedAddress = addressService.saveAddress(address);
            return ResponseEntity.ok(savedAddress);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error saving address: " + e.getMessage());
        }
    }

    // Retrieve address by address ID
    @GetMapping("/address/{addressId}")
    public ResponseEntity<?> getAddressById(@PathVariable Long addressId) {
        try {
            return ResponseEntity.ok(addressService.findAddressById(addressId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not find address with ID: " + addressId);
        }
    }
}

