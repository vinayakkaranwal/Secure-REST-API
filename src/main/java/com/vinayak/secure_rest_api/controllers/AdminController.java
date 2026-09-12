package com.vinayak.secure_rest_api.controllers;

import com.vinayak.secure_rest_api.dto.UserDTO;
import com.vinayak.secure_rest_api.entities.User;
import com.vinayak.secure_rest_api.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Validated
public class AdminController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(
            @PathVariable
            @Min(value = 1, message = "User ID must be greater than or equal to 1")  Long id){
        return ResponseEntity.ok(userService.findById(id));
    }


    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable
            @Min(value = 1, message = "User ID must be greater than or equal to 1") Long id,
            @RequestBody UserDTO updatedUser){
        return ResponseEntity.ok(userService.updateUser(id, updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable
            @Min(value = 1, message = "User ID must be greater than or equal to 1") Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully.");
    }

}
