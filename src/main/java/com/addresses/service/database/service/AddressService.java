package com.addresses.service.database.service;

import com.addresses.service.database.entity.Address;
import com.addresses.service.database.entity.User;
import com.addresses.service.database.repository.AddressRepository;
import com.addresses.service.database.repository.UserRepository;
import com.addresses.service.web.dto.AddressDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public Address saveAddress(Address address) throws Exception {
        Long userId = address.getUserId();
        long addressCount = addressRepository.countByUser_Id(userId);

        if (isDuplicateAddress(address)) {
            throw new IllegalStateException("This address already exists for the user.");
        }

        if (addressCount < 3) {
            if (address.getUserId() != null) {
                User user = userRepository.findById(address.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
                address.setUser(user);
                address.initializeDeliveryDate();
            }
            updateDefaultAddress(address);
            return addressRepository.save(address);
        } else {
            throw new Exception("A user can only have a maximum of three addresses.");
        }
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public void updateDefaultAddress(Address address) {
        if(address.getDefaultAddress()){
           List<Address> addressList = addressRepository.findByUser_Id(address.getUserId());
            addressList.forEach(each -> {
                each.setDefaultAddress(false);
                addressRepository.save(each);
            });
        }
    }

    public Address updateAddress(AddressDTO addressDTO) {
        Address address = addressRepository.findById(addressDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Address not found with ID: " + addressDTO.getId()));

        address.setStreetNumber(addressDTO.getStreetNumber());
        address.setStreet(addressDTO.getStreet());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setPostOffice(addressDTO.getPostOffice());
        address.populateUserId();
        updateDefaultAddress(address);

        return addressRepository.save(address);
    }

    public void deleteAddress(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new EntityNotFoundException("Address not found with ID: " + id);
        }
        addressRepository.deleteById(id);
    }

    public List<Address> findAddressesByUserId(Long userId) {
        // Use the repository method to find addresses by user ID
        List<Address> addresses = addressRepository.findByUser_Id(userId);
        addresses.forEach(Address::populateUserId);
        return addresses;
    }

    public Address findAddressById(Long addressId) {
        Optional<Address> addressOptional = addressRepository.findById(addressId);
        Address address = addressOptional.orElseThrow(() -> new RuntimeException("Address not found"));
        address.populateUserId();
        return address;
    }

    public boolean isDuplicateAddress(Address address) {
        Optional<User> userOptional = userRepository.findById(address.getUserId());
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found with ID: " + address.getUserId());
        }
        User user = userOptional.get();

        List<Address> existingAddresses = addressRepository.findByUserAndStreetNumberAndStreetAndPostalCodeAndPostOffice(
                user,
                address.getStreetNumber(),
                address.getStreet(),
                address.getPostalCode(),
                address.getPostOffice());

        return !existingAddresses.isEmpty();
    }
}
