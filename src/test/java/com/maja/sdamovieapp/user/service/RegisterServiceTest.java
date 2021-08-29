package com.maja.sdamovieapp.user.service;

import com.maja.sdamovieapp.config.ContainersEnvironment;
import com.maja.sdamovieapp.user.dto.RegisterRequestDTO;
import com.maja.sdamovieapp.user.entity.User;
import com.maja.sdamovieapp.user.enums.ClientTypeEnum;
import com.maja.sdamovieapp.user.enums.RoleNameEnum;
import com.maja.sdamovieapp.user.exceptions.UserAlreadyExistException;
import com.maja.sdamovieapp.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
class RegisterServiceTest extends ContainersEnvironment {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private UserRepository userRepository;


    @Test
    @DisplayName("Rejestruje nowego uzytkownika i zapisuje do bazy danych")
    @Transactional
    void shouldRegisterNewUserAndSaveIntoDB() {

        //when
        registerService.signup(getRequestDTO());

        //then
        assertThat(userRepository.findUserByEmail("test@test.pl").get().getEmail())
                .isEqualTo(getRequestDTO().getEmail());
    }

    @Test
    @DisplayName("Rzuca wyjątek jeśli podany login i email już istnieją")
    void shouldThrowExceptionWhenLoginAndEmailAlreadyExists() {

        //given
        var login = "testowy";
        var email = "test@test.pl";
        User user = getUser(login, email);
        userRepository.save(user);

        //when&then
        assertThrows(UserAlreadyExistException.class, () -> registerService.signup(getRequestDTO()));
    }

    @Test
    @DisplayName("Rzuca wyjątek jeśli podany login już istnieje")
    void shouldThrowExceptionWhenLoginAlreadyExists() {

        //given
        var login = "testowy";
        var email = "test.test@test.pl";
        User user = getUser(login, email);
        userRepository.save(user);

        //when&then
        assertThrows(UserAlreadyExistException.class, () -> registerService.signup(getRequestDTO()));
    }

    @Test
    @DisplayName("Rzuca wyjątek jeśli podany email już istnieje")
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        //given
        var login = "test123";
        var email = "test@test.pl";
        User user = getUser(login, email);
        userRepository.save(user);

        //when&then
        assertThrows(UserAlreadyExistException.class, () -> registerService.signup(getRequestDTO()));
    }

    private RegisterRequestDTO getRequestDTO() {
        return new RegisterRequestDTO(
                "Jan",
                "Testowy",
                "testowy",
                "test@test.pl",
                "Test1!"
        );
    }

    private User getUser(String login, String email) {
        User user = new User();
        user.setFirstName("Jan");
        user.setLastName("Testowy");
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword("Test1!");
        user.setCreatedAt(LocalDateTime.now());
        user.setActive(true);
        user.setClientType(ClientTypeEnum.STANDARD);
        user.setRole(RoleNameEnum.ROLE_USER);
        return user;
    }
}