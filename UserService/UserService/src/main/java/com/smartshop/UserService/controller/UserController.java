package com.smartshop.UserService.controller;

import com.smartshop.UserService.api_response.dto.UserCreateDTO;
import com.smartshop.UserService.api_response.dto.UserResponseDTO;
import com.smartshop.UserService.api_response.dto.UserResponseMailDTO;
import com.smartshop.UserService.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "APIs for managing users in the system")
public class UserController {

    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details and returns the created user's information.")
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        UserResponseDTO response = userService.createUser(userCreateDTO);
        logger.info("Created user with ID: {}", response.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get all users", description = "Retrieves a list of all users in the system.")
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Get user by ID", description = "Retrieves the details of a user by their unique ID.")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(summary = "Check if user exists by ID", description = "Checks if a user exists in the system with the given ID and returns true or false.")
    @PostMapping("/exist/{id}")
    public ResponseEntity<Boolean> isUserExistWithThisId(@PathVariable String id) {
        boolean exists = userService.isUserExistWithThisId(id);
        logger.info("Checked existence of user with ID: {}, exists: {}", id, exists);
        return ResponseEntity.ok(exists);
    }

    @Operation(summary = "Get user email and name by ID", description = "Retrieves the email and name of a user by their unique ID.")
    @GetMapping("/mail/{id}")
    public ResponseEntity<UserResponseMailDTO> getUserMailAndNameById(@PathVariable String id) {
        UserResponseMailDTO user = userService.getUserMailById(id);
        logger.info("Retrieved email for user ID: {}, email: {}", id, user.getEmail());
        return ResponseEntity.ok(user);
    }
}
