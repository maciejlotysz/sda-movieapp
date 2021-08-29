package com.maja.sdamovieapp.movie.service;

import com.maja.sdamovieapp.movie.dto.MovieDTO;
import com.maja.sdamovieapp.movie.mapper.MovieMapper;
import com.maja.sdamovieapp.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Value("${movieapp.movie.display.number}")
    private int displayMoviesNumber;

    public List<MovieDTO> getLastAddedMovies() {

        var numberMoviesToDisplay = PageRequest.ofSize(displayMoviesNumber);
        var movies = movieRepository.findAllByOrderByAddedAtDesc(numberMoviesToDisplay);
        return movieMapper.mapToMovieDtoList(movies);
    }
}
