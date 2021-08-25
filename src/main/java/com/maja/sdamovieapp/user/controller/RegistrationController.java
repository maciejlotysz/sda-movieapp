package com.maja.sdamovieapp.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class RegistrationController {

    @GetMapping("/signup")
    public String signin() {
        return "Tutaj powstanie endpoint do rejestracji użytkownika";
    }
}
