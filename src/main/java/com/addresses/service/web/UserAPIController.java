package com.addresses.service.web;


import com.addresses.service.database.entity.User;
import com.addresses.service.database.service.UserService;
import com.addresses.service.service.UserDTOService;
import com.addresses.service.web.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Save a new user", description = "Saves a new user with the provided information.")
    @ApiResponse(responseCode = "200", description = "Successfully saved the user",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDTO.class))})
    @ApiResponse(responseCode = "400", description = "Bad request, error saving user",
            content = @Content)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "UserDTO object containing the new user information", required = true,
            content = @Content(schema = @Schema(implementation = UserDTO.class)))
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) {
        try {
            User savedUser = userService.saveUser(userDTOService.convertToEntity(userDTO));
            return ResponseEntity.ok(userDTOService.convertToDTO(savedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error saving user: " + e.getMessage());
        }
    }
}
