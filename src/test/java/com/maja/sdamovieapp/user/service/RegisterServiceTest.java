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

    RegisterRequestDTO requestDTO = new RegisterRequestDTO(
            "Jan",
            "Testowy",
            "testowy",
            "test@test.pl",
            "Test1!"
    );

    @Test
    @DisplayName("Rejestruje nowego uzytkownika i zapisuje do bazy danych")
    @Transactional
    void shouldRegisterNewUserAndSaveIntoDB() {

        //when
        registerService.signup(requestDTO);

        //then
        assertThat(userRepository.findUserByEmail("test@test.pl").get().getEmail())
                .isEqualTo(requestDTO.getEmail());

    }

    @Test
    @DisplayName("Rzuca wyjątek jeśli podany login i email już istnieją")
    void shouldThrowExceptionWhenLoginAndEmailAlreadyExists() {

        //given
        var lastName = "Testowy";
        var firstName = "Jan";
        var login = "testowy";
        var email = "test@test.pl";
        var password = "Test1!";
        var roleNameEnum = RoleNameEnum.ROLE_USER;
        var clientTypeEnum = ClientTypeEnum.STANDARD;
        var createdAt = LocalDateTime.now();

        User user = getUser(lastName, firstName, login, email, password, createdAt, clientTypeEnum, roleNameEnum);
        userRepository.save(user);

        //when&then
        assertThrows(UserAlreadyExistException.class, () -> registerService.signup(requestDTO));
    }

    @Test
    @DisplayName("Rzuca wyjątek jeśli podany login już istnieje")
    void shouldThrowExceptionWhenLoginAlreadyExists() {

        //given
        var lastName = "Testowy";
        var firstName = "Jan";
        var login = "testowy";
        var email = "test_test@test.pl";
        var password = "Test1!";
        var roleNameEnum = RoleNameEnum.ROLE_USER;
        var clientTypeEnum = ClientTypeEnum.STANDARD;
        var createdAt = LocalDateTime.now();

        User user = getUser(lastName, firstName, login, email, password, createdAt, clientTypeEnum, roleNameEnum);
        userRepository.save(user);

        //when&then
        assertThrows(UserAlreadyExistException.class, () -> registerService.signup(requestDTO));
    }

    @Test
    @DisplayName("Rzuca wyjątek jeśli podany email już istnieje")
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        //given
        var lastName = "Testowy";
        var firstName = "Jan";
        var login = "test_test";
        var email = "test@test.pl";
        var password = "Test1!";
        var roleNameEnum = RoleNameEnum.ROLE_USER;
        var clientTypeEnum = ClientTypeEnum.STANDARD;
        var createdAt = LocalDateTime.now();

        User user = getUser(lastName, firstName, login, email, password, createdAt, clientTypeEnum, roleNameEnum);
        userRepository.save(user);

        //when&then
        assertThrows(UserAlreadyExistException.class, () -> registerService.signup(requestDTO));
    }

    private User getUser(String lastName,
                         String firstName,
                         String login,
                         String email,
                         String password,
                         LocalDateTime createdAt,
                         ClientTypeEnum clientTypeEnum,
                         RoleNameEnum roleNameEnum) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(password);
        user.setCreatedAt(createdAt);
        user.setActive(true);
        user.setClientType(clientTypeEnum);
        user.setRole(roleNameEnum);
        return user;
    }
}