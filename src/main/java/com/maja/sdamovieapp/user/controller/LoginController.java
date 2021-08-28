package com.maja.sdamovieapp.user.controller;

import com.maja.sdamovieapp.user.dto.LoginRequestDTO;
import com.maja.sdamovieapp.user.dto.UserDTO;
import com.maja.sdamovieapp.user.exceptions.UserNotAuthenticatedException;
import com.maja.sdamovieapp.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping(path = "/api/v1/login")
    public UserDTO signIn(@RequestBody LoginRequestDTO loginRequestDTO) {
        return loginService.login(loginRequestDTO);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler
    public void on(UserNotAuthenticatedException e) {
    }
}
