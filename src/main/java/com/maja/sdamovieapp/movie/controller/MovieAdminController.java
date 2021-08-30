package com.maja.sdamovieapp.movie.controller;

import com.maja.sdamovieapp.application.constants.ErrorCode;
import com.maja.sdamovieapp.application.exceptions.RequestExceptionHandler;
import com.maja.sdamovieapp.movie.dto.MovieRequestDTO;
import com.maja.sdamovieapp.movie.service.MovieAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class MovieAdminController {

    private final MovieAdminService adminService;

    /**
     * Endpoint do dodawania filmów do bazy
     *
     * @param movieRequestDTO obiekt typu MovieRequestDTO
     * @return ResponsEntity ze statusem 201(CREATED) jeśli fil został poprawnie dodany
     *
     * zwraca 409(CONFLICT) jeśli dodawany film już istnieje w bazie
     */

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/addMovie")
    public ResponseEntity<MovieRequestDTO> addMovie(@Valid @RequestBody MovieRequestDTO movieRequestDTO) {
        adminService.addMovie(movieRequestDTO);
        return new ResponseEntity<>(RequestExceptionHandler.buildHeadersWithMessage(ErrorCode.MOVIE_CREATED.internalCode), HttpStatus.CREATED);
    }
}
