package com.eventmaster.user_services.controller;

import com.eventmaster.user_services.model.LoginRequest;
import com.eventmaster.user_services.model.User;
import com.eventmaster.user_services.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest lg) {
        try {
            User user = userService.login(lg);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                throw new RuntimeException("Invalid credentials");
            }
        } catch (Exception e) {
            // Just rethrow so it gets picked up by your LoggingAspect
            throw e;
        }
    }

    // Change password
    @PutMapping("/change-password/{id}")
    public ResponseEntity<User> changePassword(@PathVariable Integer id, @RequestParam String newPassword) {
        User updatedUser = userService.changePassword(id, newPassword);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
