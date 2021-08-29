package com.maja.sdamovieapp.movie.service;

import com.maja.sdamovieapp.config.ContainersEnvironment;
import com.maja.sdamovieapp.movie.entity.Movie;
import com.maja.sdamovieapp.movie.enums.MovieGenreEnum;
import com.maja.sdamovieapp.movie.repository.MovieRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;

import static com.maja.sdamovieapp.movie.enums.MovieGenreEnum.*;
import static java.time.Instant.ofEpochSecond;

@ActiveProfiles("test")
@SpringBootTest
class MovieServiceTest extends ContainersEnvironment {

    @Autowired
    private MovieService service;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @DisplayName("Wyświetla określoną parametrem ilośc filmów")
    void shouldDisplaySpecifiedNumberOfMovies() {

        //given
        var movie1 = getMovie("LOTR: The Return of The King", "Peter Jackson", 2003, FANTASY, ofEpochSecond(1620243700L));
        var movie2 = getMovie("John Wick", "David Leith", 2014, ACTION, ofEpochSecond(1620253700L));
        var movie3 = getMovie("Thor: Ragnarok", "Taika Waititi", 2017, ACTION, ofEpochSecond(1622353999L));
        var movie4 = getMovie("'Deadpool 2'", "David Leitch", 2018, COMEDY, ofEpochSecond(1625353999L));

        movieRepository.save(movie1);
        movieRepository.save(movie2);
        movieRepository.save(movie3);
        movieRepository.save(movie4);

        //when
        service.getLastAddedMovies();

        //then
        Assertions.assertThat(service.getLastAddedMovies().size()).isEqualTo(2);

    }

    private Movie getMovie(String tittle, String director, int premiereYear, MovieGenreEnum genre, Instant addedAt) {
        Movie movie = new Movie();
        movie.setTitle(tittle);
        movie.setPremiereYear(premiereYear);
        movie.setMovieGenre(genre);
        movie.setDirector(director);
        movie.setAddedAt(addedAt);
        return movie;
    }

}