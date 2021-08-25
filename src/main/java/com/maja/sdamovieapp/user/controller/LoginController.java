package com.maja.sdamovieapp.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping(path = "/api/v1/signin")
    public String signup() {
        return "Tutaj będzie endpoint do logowania";
    }
}
