package com.maja.sdamovieapp.order.repository;

import com.maja.sdamovieapp.config.ContainersEnvironment;
import com.maja.sdamovieapp.movie.entity.MovieCopy;
import com.maja.sdamovieapp.movie.enums.CopyStatusEnum;
import com.maja.sdamovieapp.movie.enums.DiscTypeEnum;
import com.maja.sdamovieapp.movie.repository.MovieCopyRepository;
import com.maja.sdamovieapp.movie.entity.Movie;
import com.maja.sdamovieapp.movie.enums.MovieGenreEnum;
import com.maja.sdamovieapp.movie.repository.MovieRepository;
import com.maja.sdamovieapp.order.entity.MovieCopyOrder;
import com.maja.sdamovieapp.order.entity.Order;
import com.maja.sdamovieapp.order.enums.OrderStatusEnum;
import com.maja.sdamovieapp.user.entity.User;
import com.maja.sdamovieapp.user.enums.ClientTypeEnum;
import com.maja.sdamovieapp.user.enums.RoleNameEnum;
import com.maja.sdamovieapp.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        var user = getUser();
        var foundUserOptional = userRepository.findUserByEmail(getUser().getEmail());
        assertThat(foundUserOptional.isEmpty()).isTrue();

        //create movie
        var movie = getMovie();
        var foundMovieOptional = movieRepository.findMovieByTitle(getMovie().getTitle());
        assertThat(foundMovieOptional.isEmpty()).isTrue();

        //create related movieCopy
        var copy = getMovieCopy(movie);
        var foundMovieCopyOptional = movieCopyRepository.findByMovie_Title(getMovie().getTitle());
        assertThat(foundMovieCopyOptional.isEmpty()).isTrue();

        //create order and related copyOrder
        var oder = getOrder(user);
        var copyOrders = getMovieCopyOrders(copy, oder);
        oder.setMovieCopyOrders(copyOrders);
        copy.setMovieCopyOrders(copyOrders);
        var foundOrderOptional = orderRepository.findOrderByUser_Email(getUser().getEmail());
        assertThat(foundOrderOptional.isEmpty()).isTrue();

        //when
        userRepository.save(user);
        foundUserOptional = userRepository.findUserByEmail(getUser().getEmail());
        assertThat(foundUserOptional.isPresent()).isTrue();

        movieRepository.save(movie);
        foundMovieOptional = movieRepository.findMovieByTitle(getMovie().getTitle());
        assertThat(foundMovieOptional.isPresent()).isTrue();

        movieCopyRepository.save(copy);
        foundMovieCopyOptional = movieCopyRepository.findByMovie_Title(getMovie().getTitle());
        assertThat(foundMovieCopyOptional.isPresent()).isTrue();
        var foundCopy = foundMovieCopyOptional.get();

        orderRepository.save(oder);
        foundOrderOptional = orderRepository.findOrderByUser_Email(getUser().getEmail());
        assertThat(foundOrderOptional.isPresent()).isTrue();
        var foundOrder = foundOrderOptional.get();

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

    private Order getOrder(User user) {
        Order oder = new Order();
        oder.setUser(user);
        oder.setStartDate(now());
        oder.setEndDate(now().plusDays(5L));
        oder.setDailyRentPrice(5);
        oder.setTotalPrice(25);
        oder.setOrderStatus(OrderStatusEnum.DELIVERED);
        return oder;
    }

    private MovieCopy getMovieCopy(Movie movie) {
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

    private User getUser() {
        User user = new User();
        user.setFirstName("Jan");
        user.setLastName("Testowy");
        user.setLogin("testowy");
        user.setEmail("test@test.pl");
        user.setPassword("test123");
        user.setCreatedAt(LocalDateTime.now());
        user.setActive(true);
        user.setClientType(ClientTypeEnum.STANDARD);
        user.setRole(RoleNameEnum.ROLE_USER);
        return user;
    }
}