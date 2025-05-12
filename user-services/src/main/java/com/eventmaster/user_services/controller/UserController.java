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
    public ResponseEntity<User> login(@RequestBody LoginRequest  lg) {
        User user = userService.login(lg);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.status(401).build();
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
