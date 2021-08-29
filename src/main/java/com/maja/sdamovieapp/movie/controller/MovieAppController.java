package com.maja.sdamovieapp.movie.controller;

import com.maja.sdamovieapp.movie.dto.MovieDTO;
import com.maja.sdamovieapp.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieAppController {

    private final MovieService movieService;

    @GetMapping("/api/v1/movieapp")
    public ResponseEntity<List<MovieDTO>> displayMovies() {
        return new ResponseEntity<>(movieService.getLastAddedMovies(), HttpStatus.OK);
    }
}
