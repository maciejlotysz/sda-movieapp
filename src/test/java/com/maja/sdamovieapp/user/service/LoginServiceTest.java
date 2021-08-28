package com.maja.sdamovieapp.user.service;

import com.maja.sdamovieapp.config.ContainersEnvironment;
import com.maja.sdamovieapp.user.dto.LoginRequestDTO;
import com.maja.sdamovieapp.user.entity.User;
import com.maja.sdamovieapp.user.enums.ClientTypeEnum;
import com.maja.sdamovieapp.user.enums.RoleNameEnum;
import com.maja.sdamovieapp.user.exceptions.UserNotAuthenticatedException;
import com.maja.sdamovieapp.user.mapper.UserMapper;
import com.maja.sdamovieapp.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class LoginServiceTest extends ContainersEnvironment {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;




    @Test
    @DisplayName("Loguje pomyślnie zarejsetrowanego i aktywnego użytkownika do serwisu")
    @Transactional
    void shouldLoginUserWhenUserIsRegisterAndIsActive() {

        //given
        var id = 1L;
        var firstName = "Jan";
        var lastName = "Testowy";
        var login = "test_test";
        var email = "test@test.pl";
        var password = "Test1!";
        var roleNameEnum = RoleNameEnum.ROLE_USER;
        var createdAt = LocalDateTime.now();
        var clientTypeEnum = ClientTypeEnum.STANDARD;
        var isActive = true;

        var loginDTO = new LoginRequestDTO("test@test.pl", "Test1!");
        var user = getUser(id, firstName, lastName, login, email, passwordEncoder.encode(password), roleNameEnum, createdAt, clientTypeEnum, isActive);
        userRepository.save(user);
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        //when
        loginService.login(loginDTO);

        //then
        Assertions.assertAll(

                () -> assertThat(userMapper.mapToUserDTO(user).getEmail()).isEqualTo(user.getEmail()),
                () -> assertThat(userRepository.getById(id)).isEqualTo(user),
                () -> assertThat(userMapper.mapToUserDTO(user).getRole()).isEqualTo("ROLE_USER"),
                () -> assertThat(authentication.isAuthenticated()).isTrue()
        );
    }

    @Test
    @DisplayName("Rzuca wyjątek kiedy uzytkownik nie jest aktywny (isActive = false)")
    @Transactional
    void shouldThrowExceptionWhenUserIsNotActive() {

        //given
        var id = 1L;
        var firstName = "Jan";
        var lastName = "Testowy";
        var login = "test_test";
        var email = "test@test.pl";
        var password = "Test1!";
        var roleNameEnum = RoleNameEnum.ROLE_USER;
        var createdAt = LocalDateTime.now();
        var clientTypeEnum = ClientTypeEnum.STANDARD;
        var isActive = false;

        var loginDTO = new LoginRequestDTO("test@test.pl", "Test1!");
        var user = getUser(id, firstName, lastName, login, email, passwordEncoder.encode(password), roleNameEnum, createdAt, clientTypeEnum, isActive);
        userRepository.save(user);

        //when&then
        Assertions.assertThrows(UserNotAuthenticatedException.class, () -> loginService.login(loginDTO));
    }

    private User getUser(Long id,
                         String firstName,
                         String lastName,
                         String login,
                         String email,
                         String password,
                         RoleNameEnum roleNameEnum,
                         LocalDateTime createdAt,
                         ClientTypeEnum clientTypeEnum,
                         boolean isActive) {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(roleNameEnum);
        user.setCreatedAt(createdAt);
        user.setClientType(clientTypeEnum);
        user.setActive(isActive);
        return user;
    }

}