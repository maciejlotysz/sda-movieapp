package com.maja.sdamovieapp.user.dto;

import lombok.Value;

@Value
public class UserDTO {

    Long id;
    String firstName;
    String lastName;
    String login;
    String email;
    String role;
    boolean active;
}
