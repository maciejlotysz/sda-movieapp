package com.maja.sdamovieapp.movie.controller;

import com.maja.sdamovieapp.movie.entity.Movie;
import com.maja.sdamovieapp.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
public class MovieAppPageController {

    private final MovieRepository movieRepository;

    @GetMapping("/api/v1/movieapp")
    public Stream<String> getMoviesList() {
        return movieRepository.findAll()
                .stream()
                .map(Movie::toString);
    }
}
