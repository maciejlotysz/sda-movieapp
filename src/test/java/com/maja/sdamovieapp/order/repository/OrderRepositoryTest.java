package com.maja.sdamovieapp.order.repository;

import com.maja.sdamovieapp.config.ContainersEnvironment;
import com.maja.sdamovieapp.copy.entity.MovieCopy;
import com.maja.sdamovieapp.copy.enums.CopyStatusEnum;
import com.maja.sdamovieapp.copy.enums.DiscTypeEnum;
import com.maja.sdamovieapp.copy.repository.MovieCopyRepository;
import com.maja.sdamovieapp.movie.entity.Movie;
import com.maja.sdamovieapp.movie.enums.MovieGenreEnum;
import com.maja.sdamovieapp.movie.repository.MovieRepository;
import com.maja.sdamovieapp.order.entity.MovieCopyOrder;
import com.maja.sdamovieapp.order.entity.Order;
import com.maja.sdamovieapp.order.enums.OrderStatusEnum;
import com.maja.sdamovieapp.user.entity.User;
import com.maja.sdamovieapp.user.enums.ClientTypeEnum;
import com.maja.sdamovieapp.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class OrderRepositoryTest extends ContainersEnvironment {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieCopyRepository movieCopyRepository;

    @Test
    void shouldSaveAndFindOrderWithRelatedUserAndMovieCopy() {

        //given
        //create user
        var lastName = "Syn Gloina";
        var firstName = "Gimli";
        var login = "killerAxe";
        var email = "gimli@erebor.com";
        var password = "password";
        var isActive = true;
        var clientTypeEnum = ClientTypeEnum.STANDARD;

        User user = getUser(lastName, firstName, login, email, password, isActive, clientTypeEnum);
        Optional<User> foundUserOptional = userRepository.findUserByLastName(lastName);
        assertThat(foundUserOptional.isEmpty()).isTrue();

        //create movie
        var tittle = "LOTR: The Return of The King";
        var director = "Peter Jackson";
        var premiereYear = 2003;
        var genre = MovieGenreEnum.FANTASY;

        Movie movie = getMovie(tittle, director, premiereYear, genre);
        Optional<Movie> foundMovieOptional = movieRepository.findMovieByTitle(tittle);
        assertThat(foundMovieOptional.isEmpty()).isTrue();

        //create related movieCopy
        MovieCopy copy = getMovieCopy(movie);
        Optional<MovieCopy> foundMovieCopyOptional = movieCopyRepository.findByMovie_Title(tittle);
        assertThat(foundMovieCopyOptional.isEmpty()).isTrue();

        //create order
        var start = now();
        var end = now().plusDays(5L);
        var dailyRentPrice = 5;
        var totalPrice = 25;
        var orderStatus = OrderStatusEnum.DELIVERED;

        Order oder = getOrder(user, start, end, dailyRentPrice, totalPrice, orderStatus);

        //create related copyOrder
        List<MovieCopyOrder> copyOrders = getMovieCopyOrders(copy, oder);
        oder.setMovieCopyOrders(copyOrders);
        copy.setMovieCopyOrders(copyOrders);
        Optional<Order> foundOrderOptional = orderRepository.findOrderByUser_Email(email);
        assertThat(foundOrderOptional.isEmpty()).isTrue();

        //when
        userRepository.save(user);
        foundUserOptional = userRepository.findUserByLastName(lastName);
        assertThat(foundUserOptional.isPresent()).isTrue();

        movieRepository.save(movie);
        foundMovieOptional = movieRepository.findMovieByTitle(tittle);
        assertThat(foundMovieOptional.isPresent()).isTrue();

        movieCopyRepository.save(copy);
        foundMovieCopyOptional = movieCopyRepository.findByMovie_Title(tittle);
        assertThat(foundMovieCopyOptional.isPresent()).isTrue();
        MovieCopy foundCopy = foundMovieCopyOptional.get();

        orderRepository.save(oder);
        foundOrderOptional = orderRepository.findOrderByUser_Email(email);
        assertThat(foundOrderOptional.isPresent()).isTrue();
        Order foundOrder = foundOrderOptional.get();

        //then
        assertAll(
                () -> assertThat(foundOrder.getUser().getEmail()).isEqualTo(user.getEmail()),
                () -> assertThat(foundOrder.getMovieCopyOrders().get(0).getMovieCopy().getCopyId())
                        .isEqualTo(foundCopy.getMovieCopyOrders().get(0).getMovieCopy().getCopyId()),
                () -> assertThat(foundOrder.getMovieCopyOrders().get(0).getMovieCopy().getMovie().getTitle())
                        .isEqualTo(movie.getTitle()),
                () -> assertThat(foundOrder.getMovieCopyOrders().get(0).getMovieCopy().getCopyId()).isEqualTo(copy.getCopyId()),
                () -> assertThat(foundCopy.getMovieCopyOrders().get(0).getOrder().getUser().getClientType()).isEqualTo(user.getClientType())
        );
    }

    private List<MovieCopyOrder> getMovieCopyOrders(MovieCopy copy, Order oder) {
        MovieCopyOrder movieCopyOrder = new MovieCopyOrder();
        movieCopyOrder.setOrder(oder);
        movieCopyOrder.setMovieCopy(copy);

        List<MovieCopyOrder> copyOrders = new ArrayList<>();
        copyOrders.add(movieCopyOrder);
        return copyOrders;
    }

    private Order getOrder(User user,
                           LocalDate start,
                           LocalDate end,
                           int dailyRentPrice,
                           int totalPrice,
                           OrderStatusEnum orderStatus) {
        Order oder = new Order();
        oder.setUser(user);
        oder.setStartDate(start);
        oder.setEndDate(end);
        oder.setDailyRentPrice(dailyRentPrice);
        oder.setTotalPrice(totalPrice);
        oder.setOrderStatus(orderStatus);
        return oder;
    }

    private MovieCopy getMovieCopy(Movie movie) {
        MovieCopy copy = new MovieCopy();
        copy.setMovie(movie);
        copy.setStatus(CopyStatusEnum.AVAILABLE);
        copy.setDiscType(DiscTypeEnum.BLU_RAY);
        return copy;
    }

    private Movie getMovie(String tittle, String director, int premiereYear, MovieGenreEnum genre) {
        Movie movie = new Movie();
        movie.setTitle(tittle);
        movie.setPremiereYear(premiereYear);
        movie.setMovieGenre(genre);
        movie.setDirector(director);
        return movie;
    }

    private User getUser(String lastName,
                         String firstName,
                         String login,
                         String email,
                         String password,
                         boolean isActive,
                         ClientTypeEnum clientTypeEnum) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(password);
        user.setActive(isActive);
        user.setClientType(clientTypeEnum);
        return user;
    }
}