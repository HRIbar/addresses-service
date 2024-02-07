package com.addresses.service.web;

import com.addresses.service.database.entity.Address;
import com.addresses.service.database.service.AddressService;
import com.addresses.service.service.AddressDTOService;
import com.addresses.service.web.dto.AddressDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAddressesByUserId(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(addressDTOService.convertToDTOList(addressService.findAddressesByUserId(userId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not find addresses for user with ID: " + userId);
        }
    }

    @PostMapping
    public ResponseEntity<?> saveAddress(@RequestBody AddressDTO addressDTO) {
        try {
            Address savedAddress = addressService.saveAddress(addressDTOService.convertToEntity(addressDTO));
            return ResponseEntity.ok(addressDTOService.convertToDTO(savedAddress));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error saving address: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateAddress(@RequestBody AddressDTO addressDTO) {
        try {
            Address updatedAddress = addressService.updateAddress(addressDTO);
            AddressDTO updatedAddressDTO = addressDTOService.convertToDTO(updatedAddress); // Convert back to DTO
            return ResponseEntity.ok(updatedAddressDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating address: " + e.getMessage());
        }
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<?> getAddressById(@PathVariable Long addressId) {
        try {
            return ResponseEntity.ok(addressDTOService.convertToDTO(addressService.findAddressById(addressId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not find address with ID: " + addressId);
        }
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long addressId) {
        try {
            addressService.deleteAddress(addressId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting address: " + e.getMessage());

        }
    }
}

