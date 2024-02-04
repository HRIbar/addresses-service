package com.addresses.service.web;

import com.addresses.service.database.entity.Address;
import com.addresses.service.database.service.AddressService;
import com.addresses.service.service.AddressDTOService;
import com.addresses.service.web.dto.AddressDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses-service/address")
public class AddressAPIController {

    private final AddressService addressService;

    private final AddressDTOService addressDTOService;

    @Autowired
    public AddressAPIController(AddressService addressService, AddressDTOService addressDTOService) {
        this.addressService = addressService;
        this.addressDTOService = addressDTOService;
    }

    // Retrieve address by user's ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAddressesByUserId(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(addressDTOService.convertToDTOList(addressService.findAddressesByUserId(userId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not find addresses for user with ID: " + userId);
        }
    }

    // Store address
    @PostMapping
    public ResponseEntity<?> saveAddress(@RequestBody AddressDTO addressDTO) {
        try {
            Address savedAddress = addressService.saveAddress(addressDTOService.convertToEntity(addressDTO));
            return ResponseEntity.ok(addressDTOService.convertToDTO(savedAddress));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error saving address: " + e.getMessage());
        }
    }

    // Retrieve address by address ID
    @GetMapping("/{addressId}")
    public ResponseEntity<?> getAddressById(@PathVariable Long addressId) {
        try {
            return ResponseEntity.ok(addressDTOService.convertToDTO(addressService.findAddressById(addressId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not find address with ID: " + addressId);
        }
    }
}

