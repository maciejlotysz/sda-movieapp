package com.maja.sdamovieapp.movie.repository;

import com.maja.sdamovieapp.copy.entity.MovieCopy;
import com.maja.sdamovieapp.copy.enums.DiscTypeEnum;
import com.maja.sdamovieapp.copy.repository.MovieCopyRepository;
import com.maja.sdamovieapp.movie.entity.Movie;
import com.maja.sdamovieapp.movie.enums.MovieGenreEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
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
        String director = "Peter Jackson";
        int premiereYear = 2003;
        MovieGenreEnum genre = MovieGenreEnum.FANTASY;

        Movie movie = new Movie();
        movie.setTitle(tittle);
        movie.setPremiereYear(premiereYear);
        movie.setMovieGenre(genre);
        movie.setDirector(director);

        Optional<Movie> foundMovieOptional = movieRepository.findMovieByTitle(tittle);
        assertThat(foundMovieOptional.isEmpty()).isTrue();

        //when
        movieRepository.save(movie);
        foundMovieOptional = movieRepository.findMovieByTitle(tittle);
        assertThat(foundMovieOptional.isPresent()).isTrue();
        Movie foundMovie = foundMovieOptional.get();

        //then
        assertThat(foundMovie.getTitle()).isEqualTo(movie.getTitle());
        assertThat(foundMovie.getPremiereYear()).isEqualTo(movie.getPremiereYear());
    }

    @Test
    void shouldSaveListOfCopiesInMovieTable() {

        //given
        String tittle = "LOTR: The Return of The King";
        String director = "Peter Jackson";
        int premiereYear = 2003;
        MovieGenreEnum genre = MovieGenreEnum.FANTASY;

        Movie movie = new Movie();
        movie.setTitle(tittle);
        movie.setPremiereYear(premiereYear);
        movie.setMovieGenre(genre);
        movie.setDirector(director);

        MovieCopy copy1 = new MovieCopy();
        copy1.setMovie(movie);
        copy1.setDiscType(DiscTypeEnum.BLU_RAY);
        MovieCopy copy2 = new MovieCopy();
        copy2.setMovie(movie);
        copy2.setDiscType(DiscTypeEnum.BLU_RAY);

        List<MovieCopy> copies = new ArrayList<>();
        copies.add(copy1);
        copies.add(copy2);
        movie.setCopies(copies);

        Optional<Movie> foundMovieOptional = movieRepository.findMovieByTitle(tittle);
        assertThat(foundMovieOptional.isEmpty()).isTrue();


        //when
        movieRepository.save(movie);
        foundMovieOptional = movieRepository.findMovieByTitle(tittle);
        assertThat(foundMovieOptional.isPresent()).isTrue();
        Movie foundMovie = foundMovieOptional.get();

        List<MovieCopy> foundCopies = movieCopyRepository.findAll();

        //then
        assertThat(foundMovie.getTitle()).isEqualTo(movie.getTitle());
        assertThat(foundMovie.getPremiereYear()).isEqualTo(movie.getPremiereYear());

        assertThat(foundCopies.isEmpty()).isFalse();
    }
}