package com.maja.sdamovieapp.movie.repository;

import com.maja.sdamovieapp.copy.entity.MovieCopy;
import com.maja.sdamovieapp.copy.enums.DiscTypeEnum;
import com.maja.sdamovieapp.copy.repository.MovieCopyRepository;
import com.maja.sdamovieapp.movie.entity.Movie;
import com.maja.sdamovieapp.movie.enums.MovieGenreEnum;
import com.maja.sdamovieapp.user.entity.DeliveryAddress;
import com.maja.sdamovieapp.user.entity.User;
import com.maja.sdamovieapp.user.enums.ClientTypeEnum;
import com.maja.sdamovieapp.user.repository.DeliveryAddressRepository;
import com.maja.sdamovieapp.user.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeliveryAddressRepository addressRepository;

    @Test
    void shouldSaveMovieInDatabase() {

        //given
        String tittle = "LOTR: The Return of The King";
        String director = "Peter Jackson";
        int premiereYear = 2003;
        MovieGenreEnum genre = MovieGenreEnum.FANTASY;

        Movie movie = new Movie();
        movie.setTittle(tittle);
        movie.setPremiereYear(premiereYear);
        movie.setMovieGenre(genre);
        movie.setDirector(director);

        Optional<Movie> foundMovieOptional = movieRepository.findMovieByTittle(tittle);
        assertThat(foundMovieOptional.isEmpty()).isTrue();

        //when
        movieRepository.save(movie);
        foundMovieOptional = movieRepository.findMovieByTittle(tittle);
        assertThat(foundMovieOptional.isPresent()).isTrue();
        Movie foundMovie = foundMovieOptional.get();

        //then
        assertThat(foundMovie.getTittle()).isEqualTo(movie.getTittle());
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
        movie.setTittle(tittle);
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

        Optional<Movie> foundMovieOptional = movieRepository.findMovieByTittle(tittle);
        assertThat(foundMovieOptional.isEmpty()).isTrue();


        //when
        movieRepository.save(movie);
        foundMovieOptional = movieRepository.findMovieByTittle(tittle);
        assertThat(foundMovieOptional.isPresent()).isTrue();
        Movie foundMovie = foundMovieOptional.get();

        List<MovieCopy> foundCopies = movieCopyRepository.findAll();

        //then
        assertThat(foundMovie.getTittle()).isEqualTo(movie.getTittle());
        assertThat(foundMovie.getPremiereYear()).isEqualTo(movie.getPremiereYear());

        assertThat(foundCopies.isEmpty()).isFalse();
    }

    @Test
    void shouldSaveListOfAddressesInUserTable() {

        //given
        String lastName = "Syn Gloina";
        String gimli = "Gimli";
        String login = "killerAxe";
        String email = "gimli@erebor.com";
        String password = "password";
        ClientTypeEnum clientTypeEnum = ClientTypeEnum.STANDARD;

        User user = new User();
        user.setFirstName(gimli);
        user.setLastName(lastName);
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(password);
        user.setActive(true);
        user.setClientType(clientTypeEnum);

        DeliveryAddress address = new DeliveryAddress();
        address.setUser(user);
        address.setStreet("Konwaliowa");
        address.setBuildingNumber(10);
        address.setZipCode("02-002");
        address.setCity("Minas Tirith");

        List<DeliveryAddress> addresses = new ArrayList<>();
        addresses.add(address);
        user.setAddresses(addresses);

        Optional<User> foundUserOptional = userRepository.findUserByLastName(lastName);
        assertThat(foundUserOptional.isEmpty()).isTrue();

        //when
        userRepository.save(user);
        foundUserOptional = userRepository.findUserByLastName(lastName);
        assertThat(foundUserOptional.isPresent()).isTrue();
        User foundUser = foundUserOptional.get();

        List<DeliveryAddress> foundAddresses = addressRepository.findAll();

        //then

        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(foundUser.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(foundUser.getLogin()).isEqualTo(user.getLogin());

        assertThat(foundAddresses.isEmpty()).isFalse();
    }
}