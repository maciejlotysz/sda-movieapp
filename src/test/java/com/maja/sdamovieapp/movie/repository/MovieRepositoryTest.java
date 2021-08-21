package com.maja.sdamovieapp.movie.repository;

import com.maja.sdamovieapp.copy.repository.MovieCopyRepository;
import com.maja.sdamovieapp.movie.entity.Movie;
import com.maja.sdamovieapp.movie.enums.MovieGenreEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieCopyRepository movieCopyRepository;

    @Test
    void shouldSaveMovieInDatabase() {

        //given
        String tittle = "LOTR: The Return of The King";
        Movie movie = new Movie();
        movie.setTittle(tittle);
        movie.setPremiereYear(2003);
        movie.setMovieGenre(MovieGenreEnum.FANTASY);
        movie.setDirector("Peter Jackson");

        Optional<Movie> foundMovieOptional = movieRepository.findMovieByTittle(tittle);
        assertThat(foundMovieOptional.isEmpty()).isTrue();

        //when
        movieRepository.save(movie);
        foundMovieOptional = movieRepository.findMovieByTittle(tittle);

        //then
        assertThat(foundMovieOptional.isPresent()).isTrue();
        Movie foundMovie = foundMovieOptional.get();
        assertThat(foundMovie.getTittle()).isEqualTo(movie.getTittle());
        assertThat(foundMovie.getPremiereYear()).isEqualTo(movie.getPremiereYear());
    }
}