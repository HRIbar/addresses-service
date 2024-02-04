package com.addresses.service.service;

import com.addresses.service.database.entity.User;
import com.addresses.service.web.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserDTOService {

    public UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }

    public User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        return user;
    }

}
