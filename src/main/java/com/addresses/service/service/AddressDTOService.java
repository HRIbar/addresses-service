package com.addresses.service.service;

import com.addresses.service.database.entity.Address;
import com.addresses.service.database.entity.User;
import com.addresses.service.web.dto.AddressDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

import java.util.List;

@Service
public class AddressDTOService {

    public AddressDTO convertToDTO(Address address) {
        AddressDTO dto = new AddressDTO();
            dto.setId(address.getId());
            dto.setStreetNumber(address.getStreetNumber());
            dto.setStreet(address.getStreet());
            dto.setPostalCode(address.getPostalCode());
            dto.setPostOffice(address.getPostOffice());
            dto.setUserId(address.getUserId());
            dto.setDefaultAddress(address.getDefaultAddress());
            dto.setDeliveryDate(address.getDeliveryDate());
        return dto;
    }

    public Address convertToEntity(AddressDTO addressDTO) {
        Address address = new Address();
        // Set properties from DTO to Address entity
        address.setStreetNumber(addressDTO.getStreetNumber());
        address.setStreet(addressDTO.getStreet());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setPostOffice(addressDTO.getPostOffice());
        address.setUserId(addressDTO.getUserId());
        address.setDefaultAddress(addressDTO.getDefaultAddress());
        address.setDeliveryDate(addressDTO.getDeliveryDate());
        return address;
    }

    public List<AddressDTO> convertToDTOList(List<Address> addressList) {
        return  addressList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<Address> convertToEntityList(List<AddressDTO> addressDTOList) {
        return addressDTOList.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }
}
