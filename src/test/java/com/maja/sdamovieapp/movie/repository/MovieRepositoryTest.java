package com.maja.sdamovieapp.movie.repository;

import com.maja.sdamovieapp.config.ContainersEnvironment;
import com.maja.sdamovieapp.copy.entity.MovieCopy;
import com.maja.sdamovieapp.copy.enums.CopyStatusEnum;
import com.maja.sdamovieapp.copy.enums.DiscTypeEnum;
import com.maja.sdamovieapp.copy.repository.MovieCopyRepository;
import com.maja.sdamovieapp.movie.entity.Movie;
import com.maja.sdamovieapp.movie.enums.MovieGenreEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class MovieRepositoryTest extends ContainersEnvironment {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieCopyRepository movieCopyRepository;

    @Test
    void shouldSaveMovieInDatabase() {

        //given
        var movie = getMovie();
        var foundMovieOptional = movieRepository.findMovieByTitle(getMovie().getTitle());
        assertThat(foundMovieOptional.isEmpty()).isTrue();

        //when
        movieRepository.save(movie);
        foundMovieOptional = movieRepository.findMovieByTitle(getMovie().getTitle());
        assertThat(foundMovieOptional.isPresent()).isTrue();
        var foundMovie = foundMovieOptional.get();

        //then
        assertThat(foundMovie.getTitle()).isEqualTo(movie.getTitle());
        assertThat(foundMovie.getPremiereYear()).isEqualTo(movie.getPremiereYear());
    }

    @Test
    void shouldSaveListOfCopiesInMovieTable() {

        //given
        var movie = getMovie();
        var copy1 = getCopy(movie);
        var copy2 = getCopy(movie);
        getCopiesList(movie, copy1, copy2);
        var foundMovieOptional = movieRepository.findMovieByTitle(getMovie().getTitle());
        assertThat(foundMovieOptional.isEmpty()).isTrue();

        //when
        movieRepository.save(movie);
        foundMovieOptional = movieRepository.findMovieByTitle(getMovie().getTitle());
        assertThat(foundMovieOptional.isPresent()).isTrue();
        var foundMovie = foundMovieOptional.get();
        var foundCopies = movieCopyRepository.findAll();

        //then
        assertThat(foundMovie.getTitle()).isEqualTo(movie.getTitle());
        assertThat(foundMovie.getPremiereYear()).isEqualTo(movie.getPremiereYear());
        assertThat(foundCopies.isEmpty()).isFalse();
    }

    private void getCopiesList(Movie movie, MovieCopy copy1, MovieCopy copy2) {
        List<MovieCopy> copies = new ArrayList<>();
        copies.add(copy1);
        copies.add(copy2);
        movie.setCopies(copies);
    }

    private MovieCopy getCopy(Movie movie) {
        MovieCopy copy = new MovieCopy();
        copy.setMovie(movie);
        copy.setStatus(CopyStatusEnum.AVAILABLE);
        copy.setDiscType(DiscTypeEnum.BLU_RAY);
        return copy;
    }

    private Movie getMovie() {
        Movie movie = new Movie();
        movie.setTitle("LOTR: The Return of The King");
        movie.setPremiereYear(2003);
        movie.setMovieGenre(MovieGenreEnum.FANTASY);
        movie.setDirector("Peter Jackson");
        movie.setAddedAt(Instant.now());
        return movie;
    }
}