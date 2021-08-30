package com.maja.sdamovieapp.movie.service;

import com.maja.sdamovieapp.application.constants.ErrorCode;
import com.maja.sdamovieapp.movie.dto.MovieRequestDTO;
import com.maja.sdamovieapp.movie.entity.Movie;
import com.maja.sdamovieapp.movie.exceptions.MovieAlreadyExistsException;
import com.maja.sdamovieapp.movie.mapper.MovieMapper;
import com.maja.sdamovieapp.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieAdminService {

    private final MovieRepository movieRepository;

    private final MovieMapper movieMapper;

    @Transactional
    public void addMovie(MovieRequestDTO requestDTO) {
        movieRepository.findMovieByTitleAndPremiereYearAndDirector(
                requestDTO.getTitle(), requestDTO.getPremiereYear(), requestDTO.getDirector())
               .ifPresent(movie -> validateExistingMovie(requestDTO, movie));

        var mappedMovie = movieMapper.mapToMovie(requestDTO);
        mappedMovie.setAddedAt(Instant.now());
        movieRepository.save(mappedMovie);
    }

    private void validateExistingMovie(MovieRequestDTO requestDTO, Movie movie) {
        if (movie.getTitle().equals(requestDTO.getTitle()) && movie.getPremiereYear().equals(requestDTO.getPremiereYear())
                && movie.getDirector().equals(requestDTO.getDirector())) {

            log.warn("Movie with title " + requestDTO.getTitle() + " and Director " + requestDTO.getDirector()
                    + " from " + requestDTO.getPremiereYear() + " is already in database");

            throw new MovieAlreadyExistsException(ErrorCode.MOVIE_ALREADY_EXISTS.internalCode);
        }
    }
}