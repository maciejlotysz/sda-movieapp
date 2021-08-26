package com.maja.sdamovieapp.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {

    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private String password;
}
