package com.smartshop.UserService.services;

import com.smartshop.UserService.api_response.dto.UserCreateDTO;
import com.smartshop.UserService.api_response.dto.UserResponseDTO;
import com.smartshop.UserService.entity.User;
import com.smartshop.UserService.exceptions.UserNotFoundException;
import com.smartshop.UserService.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {
        String uuid = UUID.randomUUID().toString();
        User user = new User();
        user.setId(uuid);
        user.setName(userCreateDTO.getName());
        user.setEmail(userCreateDTO.getEmail());
        user.setPassword(userCreateDTO.getPassword());
        user.setRole(userCreateDTO.getRole());
        
        User savedUser = repository.save(user);
        return new UserResponseDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getRole());
    }

    @Override
    public UserResponseDTO getUserById(String id) {
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        UserResponseDTO response = UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
        return response;
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = repository.findAll();
        return users.stream()
                .map(user -> new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole()))
                .toList();
    }

    @Override
    public UserResponseDTO updateUser(String id, User user) {
        User existingUser = repository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setRole(user.getRole());

        User updatedUser = repository.save(existingUser);
        return UserResponseDTO.builder()
                .id(updatedUser.getId())
                .name(updatedUser.getName())
                .email(updatedUser.getEmail())
                .role(updatedUser.getRole())
                .build();
    }

    @Override
    public void deleteUser(String id) {
        User existingUser = repository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        repository.delete(existingUser);
    }

    @Override
    public boolean isUserExistWithThisId(String id) {
        return repository.existsById(id);
    }
}
