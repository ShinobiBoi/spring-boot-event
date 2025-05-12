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
        // You could hash the password here before saving (for production)
        return userRepo.save(user);
    }


    public User login(LoginRequest lg) {
        Optional<User> optionalUser = userRepo.findByEmail(lg.getEmail());
        if (optionalUser.isPresent() && optionalUser.get().getPassword().equals(lg.getPassword())) {
            return optionalUser.get();
        }
        return null;
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
}