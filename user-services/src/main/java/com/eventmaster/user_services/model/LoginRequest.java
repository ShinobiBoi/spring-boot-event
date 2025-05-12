package com.eventmaster.user_services.model;


import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;

}
