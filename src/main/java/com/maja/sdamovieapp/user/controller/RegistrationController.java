package com.maja.sdamovieapp.user.controller;

import com.maja.sdamovieapp.application.constants.ErrorCode;
import com.maja.sdamovieapp.user.dto.RegisterRequestDTO;
import com.maja.sdamovieapp.user.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegisterService registerService;

    /**
     * Endpoint do rejestracji użytkownika
     *
     * @param registerRequestDTO obiekt typu RegisterRequestDTO
     * @return ResponseEntity ze statusem 201(CREATED) jeśli user został pomyślnie zarejestrowany
     *
     * zwraca 409(CONFLICT) jesśli user już istnieje w bazie
     */

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {
        registerService.signup(registerRequestDTO);
        return new ResponseEntity<>(ErrorCode.REGISTRATION_SUCCEED.internalCode, HttpStatus.CREATED);
    }
}
