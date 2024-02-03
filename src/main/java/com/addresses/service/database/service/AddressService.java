package com.addresses.service.database.service;

import com.addresses.service.database.entity.Address;
import com.addresses.service.database.entity.User;
import com.addresses.service.database.repository.AddressRepository;
import com.addresses.service.database.repository.UserRepository;
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

    public Address saveAddress(Address address) {
        if (address.getUserId() != null) {
            User user = userRepository.findById(address.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
            address.setUser(user);
        }

        return addressRepository.save(address);
    }

    public Optional<Address> getAddressById(Long id) {
        return addressRepository.findById(id);
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Address updateAddress(Address address) {
        return addressRepository.save(address);
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    public Optional<List<Address>> findAddressesByUserId(Long userId) {
        // Use the repository method to find addresses by user ID
        List<Address> addresses = addressRepository.findByUser_Id(userId);
        return Optional.ofNullable(addresses.isEmpty() ? null : addresses);
    }

    public Optional<Address>  findAddressById(Long addressId) {
        return addressRepository.findById(addressId);
    }
}
