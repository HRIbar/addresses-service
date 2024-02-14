package com.addresses.service.web;

import com.addresses.service.database.entity.Address;
import com.addresses.service.database.service.AddressService;
import com.addresses.service.service.AddressDTOService;
import com.addresses.service.service.AddressValidationService;
import com.addresses.service.web.dto.AddressDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    private final AddressValidationService addressValidationService;

    @Autowired
    public AddressAPIController(AddressService addressService, AddressDTOService addressDTOService, AddressValidationService addressValidationService) {
        this.addressService = addressService;
        this.addressDTOService = addressDTOService;
        this.addressValidationService = addressValidationService;
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get addresses by user ID",
            description = "Retrieves a list of addresses associated with the specified user ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of addresses",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AddressDTO.class))})
    @ApiResponse(responseCode = "400", description = "Bad request, could not find addresses for the specified user ID",
            content = @Content)
    @Parameter(name = "userId", description = "User ID to retrieve addresses for", required = true)

    public ResponseEntity<?> getAddressesByUserId(@PathVariable Long userId) {
        try {
            System.out.println("getAddressesByUserId invoked");
            return ResponseEntity.ok(addressDTOService.convertToDTOList(addressService.findAddressesByUserId(userId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not find addresses for user with ID: " + userId);
        }
    }

    @PostMapping
    @Operation(summary = "Save a new address",
            description = "Saves a new address with the provided information. Validates postal code and post office combination.")
    @ApiResponse(responseCode = "200", description = "Successfully saved the address",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AddressDTO.class))})
    @ApiResponse(responseCode = "400", description = "Bad request, error saving address or invalid postal code and post office combination",
            content = @Content)
    @ApiResponse(responseCode = "500", description = "Error saving address",
            content = @Content)
    public ResponseEntity<?> saveAddress(@RequestBody AddressDTO addressDTO) {
        try {
            System.out.println("saveAddress invoked");
            if (!addressValidationService.isValidPostalCodeCombination(
                    addressDTO.getPostalCode(), addressDTO.getPostOffice())) {
                return ResponseEntity.badRequest().body("Invalid postal code and post office combination.");
            }

            Address savedAddress = addressService.saveAddress(addressDTOService.convertToEntity(addressDTO));
            return ResponseEntity.ok(addressDTOService.convertToDTO(savedAddress));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving address: " + e.getMessage());
        }
    }

    @PutMapping
    @Operation(summary = "Update address")
    @ApiResponse(responseCode = "200", description = "Address updated",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AddressDTO.class))})
    @ApiResponse(responseCode = "400", description = "Invalid postal code and post office combination.",
            content = @Content)
    @ApiResponse(responseCode = "404", description = "Address not found",
            content = @Content)
    @ApiResponse(responseCode = "500", description = "Error updating address",
            content = @Content)
    public ResponseEntity<?> updateAddress(@RequestBody AddressDTO addressDTO) {
        try {
            System.out.println("updateAddress invoked");
            if (!addressValidationService.isValidPostalCodeCombination(
                    addressDTO.getPostalCode(), addressDTO.getPostOffice())) {
                return ResponseEntity.badRequest().body("Invalid postal code and post office combination.");
            }

            Address updatedAddress = addressService.updateAddress(addressDTO);
            AddressDTO updatedAddressDTO = addressDTOService.convertToDTO(updatedAddress); // Convert back to DTO
            return ResponseEntity.ok(updatedAddressDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating address: " + e.getMessage());
        }
    }

    @GetMapping("/{addressId}")
    @Operation(summary = "Get an address by its ID")
    @ApiResponse(responseCode = "200", description = "Found the address",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AddressDTO.class))})
    @ApiResponse(responseCode = "400", description = "Could not find address with the given ID",
            content = @Content)
    public ResponseEntity<?> getAddressById(@PathVariable Long addressId) {
        try {
            System.out.println("getAddressById invoked");
            return ResponseEntity.ok(addressDTOService.convertToDTO(addressService.findAddressById(addressId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not find address with ID: " + addressId);
        }
    }

    @DeleteMapping("/{addressId}")
    @Operation(summary = "Delete an Address")
    @ApiResponse(responseCode = "200", description = "Ok",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AddressDTO.class))})
    @ApiResponse(responseCode = "404", description = "Address not found",
            content = @Content)
    @ApiResponse(responseCode = "500", description = "Error deleting address",
            content = @Content)
    public ResponseEntity<?> deleteAddress(@PathVariable Long addressId) {
        try {
            System.out.println("deleteAddress invoked");
            addressService.deleteAddress(addressId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting address: " + e.getMessage());

        }
    }
}

