package com.addresses.service.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class AddressValidationService {

    private Map<Integer, String> validPostalCodes = new HashMap<>();

    @PostConstruct
    public void init() {
        loadPostalCodes();
    }

    private void loadPostalCodes() {
        try {
            // Using ClassPathResource to load the file from src/main/resources
            ClassPathResource resource = new ClassPathResource("postalCodes.txt");

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

                validPostalCodes = reader.lines()
                        .map(line -> line.split(","))
                        .collect(Collectors.toMap(
                                parts -> Integer.parseInt(parts[0].trim()), // Postal code as key
                                parts -> parts[1].trim() // Postal name as value
                        ));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load postal codes from file", e);
        }
    }

    public boolean isValidPostalCodeCombination(Integer postalCode, String postOffice) {
        String validPostOffice = validPostalCodes.get(postalCode);
        return validPostOffice != null && validPostOffice.equalsIgnoreCase(postOffice);
    }
}
