package com.maja.sdamovieapp.user.dto;

import lombok.Value;

@Value
public class RegisterRequestDTO {

    String firstName;
    String lastName;
    String login;
    String email;
    String password;
}
