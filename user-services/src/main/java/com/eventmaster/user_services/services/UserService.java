package com.eventmaster.user_services.services;

import com.eventmaster.user_services.model.LoginRequest;
import com.eventmaster.user_services.model.User;
import com.eventmaster.user_services.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;


    public User registerUser(User user) {
        return userRepo.save(user);
    }


    // UserService.java
    public User login(LoginRequest lg) {
        User user = userRepo.findByEmail(lg.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + lg.getEmail()));

        if (!lg.getPassword().equals(user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        return user;
    }

    public User changePassword(Integer id, String newPassword) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(newPassword);
            return userRepo.save(user);
        }
        return null;
    }


    public void deleteUser(Integer id) {
        userRepo.deleteById(id);
    }


    // Custom exceptions
    public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException(String message) {
            super(message);
        }
    }

}