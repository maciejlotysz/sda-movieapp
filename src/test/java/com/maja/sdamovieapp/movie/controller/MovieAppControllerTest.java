package com.maja.sdamovieapp.movie.controller;

import com.maja.sdamovieapp.config.ContainersEnvironment;
import com.maja.sdamovieapp.movie.entity.Movie;
import com.maja.sdamovieapp.movie.enums.MovieGenreEnum;
import com.maja.sdamovieapp.movie.repository.MovieRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.maja.sdamovieapp.movie.enums.MovieGenreEnum.*;
import static java.time.Instant.ofEpochSecond;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class MovieAppControllerTest extends ContainersEnvironment {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MovieRepository movieRepository;

    @Value("${movieapp.movie.display.number}")
    private int pageable;


    @Test
    @DisplayName("Wyświetla ostatnio dodane filmy")
    void shouldDisplayMoviesAndReturnStatus200Ok() throws Exception {

        //given
        var movies = getMovies();
        var display = PageRequest.ofSize(pageable);
        when(movieRepository.findAllByOrderByAddedAtDesc(display)).thenReturn(movies);

        //when
        var response = mvc.perform(displayMovies()).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    private List<Movie> getMovies() {
        var movie1 = getMovie("LOTR: The Return of The King", "Peter Jackson", 2003, FANTASY, ofEpochSecond(1620243700L));
        var movie2 = getMovie("John Wick", "David Leith", 2014, ACTION, ofEpochSecond(1620253700L));
        var movie3 = getMovie("Thor: Ragnarok", "Taika Waititi", 2017, ACTION, ofEpochSecond(1622353999L));
        var movie4 = getMovie("'Deadpool 2'", "David Leitch", 2018, COMEDY, ofEpochSecond(1625353999L));
        List<Movie> movies = new ArrayList<>();
        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);
        movies.add(movie4);
        return movies;
    }

    private MockHttpServletRequestBuilder displayMovies() {
        return MockMvcRequestBuilders.get("/api/v1/movieapp")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
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