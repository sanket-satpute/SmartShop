package com.smartshop.UserService.services;

import com.smartshop.UserService.api_response.dto.UserCreateDTO;
import com.smartshop.UserService.api_response.dto.UserResponseDTO;
import com.smartshop.UserService.entity.User;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserCreateDTO user);

    UserResponseDTO getUserById(String id);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO updateUser(String id, User user);

    void deleteUser(String id);
}
