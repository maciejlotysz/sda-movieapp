package com.maja.sdamovieapp.user.service;

import com.maja.sdamovieapp.security.config.UserInfo;
import com.maja.sdamovieapp.user.dto.LoginRequestDTO;
import com.maja.sdamovieapp.user.dto.UserDTO;
import com.maja.sdamovieapp.user.exceptions.UserNotAuthenticatedException;
import com.maja.sdamovieapp.user.mapper.UserMapper;
import com.maja.sdamovieapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    @Transactional(readOnly = true)
    public UserDTO login(LoginRequestDTO loginDTO) {

        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        try {
            var authResult = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            var userInfo = (UserInfo) authResult.getPrincipal();
            var user = userRepository.getById(userInfo.getId());
            return userMapper.mapToUserDTO(user);
        } catch (AuthenticationException e) {
            throw new UserNotAuthenticatedException();
        }
    }
}
