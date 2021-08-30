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
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        var loginDTO = new LoginRequestDTO("test@test.pl", "Test1!");
        var user = getUser(true);
        userRepository.save(user);

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        //when
        loginService.login(loginDTO);

        //then
        Assertions.assertAll(
                () -> assertThat(userMapper.mapToUserDTO(user).getEmail()).isEqualTo(user.getEmail()),
                () -> assertThat(userRepository.getById(1L)).isEqualTo(user),
                () -> assertThat(userMapper.mapToUserDTO(user).getRole()).isEqualTo("ROLE_USER"),
                () -> assertThat(authentication.isAuthenticated()).isTrue()
        );
    }

    @Test
    @DisplayName("Rzuca wyjątek kiedy uzytkownik nie jest aktywny (isActive = false)")
    @Transactional
    void shouldThrowExceptionWhenUserIsNotActive() {

        //given
        var loginDTO = new LoginRequestDTO("test@test.pl", "Test1!");
        var user = getUser(false);
        userRepository.save(user);

        //when&then
        assertThrows(UserNotAuthenticatedException.class, () -> loginService.login(loginDTO));
    }

    private User getUser(boolean isActive) {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Jan");
        user.setLastName("Testowy");
        user.setLogin("test_test");
        user.setEmail("test@test.pl");
        user.setPassword(passwordEncoder.encode("Test1!"));
        user.setRole(RoleNameEnum.ROLE_USER);
        user.setCreatedAt(LocalDateTime.now());
        user.setClientType(ClientTypeEnum.STANDARD);
        user.setActive(isActive);
        return user;
    }
}