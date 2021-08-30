package com.maja.sdamovieapp.movie.service;

import com.maja.sdamovieapp.config.ContainersEnvironment;
import com.maja.sdamovieapp.movie.dto.MovieRequestDTO;
import com.maja.sdamovieapp.movie.entity.Movie;
import com.maja.sdamovieapp.movie.enums.MovieGenreEnum;
import com.maja.sdamovieapp.movie.exceptions.MovieAlreadyExistsException;
import com.maja.sdamovieapp.movie.repository.MovieRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static java.time.Instant.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
class MovieAdminServiceTest extends ContainersEnvironment {

    @Autowired
    private MovieAdminService adminService;

    @Autowired
    private MovieRepository movieRepository;


    @Test
    @DisplayName("Dodaje do bazy nowy film")
    void shouldAddNewMovieAndSaveInDatabase() {

        //when
        adminService.addMovie(getRequestDTO());

        //then
        assertThat(movieRepository.findMovieByTitle("Król Artur: Legenda Miecza").get().getTitle())
                .isEqualTo(getRequestDTO().getTitle());
    }

    @Test
    @DisplayName("Rzuca wyjątkiem jesli wpowadzany film już istnieje")
    void shouldThrowExceptionWhenMovieAlreadyExists() {

        //given
        var movie = getMovie("Król Artur: Legenda Miecza", "Guy Ritchie", 2017);
        movieRepository.save(movie);

        //when&then
        assertThrows(MovieAlreadyExistsException.class, ()-> adminService.addMovie(getRequestDTO()));
    }

    @Test
    @DisplayName("Dodaje do bazy nowy film jesli istnieje film z tym samym tytułem i rokie premiery ale z innym reżyserem")
    void shouldAddAndSaveMovieWithTheSameTitleAndPremiereYearButDifferentDirector() {

        //given
        var movie = getMovie("Król Artur: Legenda Miecza", "Justin Lin", 2017);
        movieRepository.save(movie);

        //when
        adminService.addMovie(getRequestDTO());

        //then
        assertThat(movieRepository.findAllByTitle("Król Artur: Legenda Miecza").size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Dodaje do bazy nowy film jesli istnieje film z tym samym tytułem i reżyserem ale innym rokiem premiery")
    void shouldAddAndSaveMovieWithTheSameTitleAndDirectorButDifferentPremiereYear() {

        //given
        var movie = getMovie("Król Artur: Legenda Miecza", "Guy Ritchie", 2021);
        movieRepository.save(movie);

        //when
        adminService.addMovie(getRequestDTO());

        //then
        assertThat(movieRepository.findAllByTitle("Król Artur: Legenda Miecza").size()).isEqualTo(2);
    }

    private MovieRequestDTO getRequestDTO() {
        return new MovieRequestDTO(
                "Król Artur: Legenda Miecza",
                2017,
                "Guy Ritchie",
                MovieGenreEnum.ADVENTURE,
                "Młody Artur zdobywa miecz Excalibur i wiedzę na temat swojego królewskiego pochodzenia. " +
                        "Przyłącza się do rebelii, aby pokonać tyrana, który zamordował jego rodziców."
        );
    }

    private Movie getMovie(String tittle, String director, int premiereYear) {
        Movie movie = new Movie();
        movie.setTitle(tittle);
        movie.setPremiereYear(premiereYear);
        movie.setMovieGenre(MovieGenreEnum.ADVENTURE);
        movie.setDirector(director);
        movie.setAddedAt(now());
        return movie;
    }
}