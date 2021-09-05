package com.maja.sdamovieapp.movie.service;

import com.maja.sdamovieapp.application.constants.ErrorCode;
import com.maja.sdamovieapp.movie.dto.CopyRequestDTO;
import com.maja.sdamovieapp.movie.dto.MovieCopyDTO;
import com.maja.sdamovieapp.movie.dto.MovieRequestDTO;
import com.maja.sdamovieapp.movie.entity.Movie;
import com.maja.sdamovieapp.movie.enums.CopyStatusEnum;
import com.maja.sdamovieapp.movie.exceptions.MovieAlreadyExistsException;
import com.maja.sdamovieapp.movie.mapper.CopyMapper;
import com.maja.sdamovieapp.movie.mapper.MovieMapper;
import com.maja.sdamovieapp.movie.repository.MovieCopyRepository;
import com.maja.sdamovieapp.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieAdminService {

    private final MovieRepository movieRepository;
    private final MovieCopyRepository copyRepository;
    private final MovieMapper movieMapper;
    private final CopyMapper copyMapper;


    @Transactional
    public void addMovie(MovieRequestDTO requestDTO) {
        movieRepository.findMovieByTitleAndPremiereYearAndDirector(
                requestDTO.getTitle(), requestDTO.getPremiereYear(), requestDTO.getDirector())
               .ifPresent(movie -> validateExistingMovie(requestDTO, movie));

        var mappedMovie = movieMapper.mapToMovie(requestDTO);
        mappedMovie.setAddedAt(Instant.now());
        movieRepository.save(mappedMovie);
    }

    public List<MovieCopyDTO> getMoviesByTitle(String title) {
        var movies = movieRepository.findMoviesByTitleIgnoreCase(title);
        return movieMapper.mapToMovieCopyDtoList(movies);
    }

    @Transactional
    public void addCopy(Long id, CopyRequestDTO copyRequestDTO) {
        var movie = movieRepository.getById(id);
        var mappedCopy = copyMapper.mapToMovieCopy(copyRequestDTO);
        mappedCopy.setMovie(movie);
        mappedCopy.setStatus(CopyStatusEnum.AVAILABLE);
        copyRepository.save(mappedCopy);
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