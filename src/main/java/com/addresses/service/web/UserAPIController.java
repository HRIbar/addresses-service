package com.addresses.service.web;


import com.addresses.service.database.entity.User;
import com.addresses.service.database.service.UserService;
import com.addresses.service.service.UserDTOService;
import com.addresses.service.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/addresses-service")
public class UserAPIController {

    private final UserService userService;

    private final UserDTOService userDTOService;

    @Autowired
    public UserAPIController(UserService userService, UserDTOService userDTOService) {
        this.userService = userService;
        this.userDTOService = userDTOService;
    }

    @PostMapping("/user")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) {
        try {
            User savedUser = userService.saveUser(userDTOService.convertToEntity(userDTO));
            return ResponseEntity.ok(userDTOService.convertToDTO(savedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error saving user: " + e.getMessage());
        }
    }
}
