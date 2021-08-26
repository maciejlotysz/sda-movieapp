package com.maja.sdamovieapp.user.service;

import com.maja.sdamovieapp.application.constants.ErrorCode;
import com.maja.sdamovieapp.user.dto.RegisterRequestDTO;
import com.maja.sdamovieapp.user.entity.User;
import com.maja.sdamovieapp.user.enums.ClientTypeEnum;
import com.maja.sdamovieapp.user.enums.RoleNameEnum;
import com.maja.sdamovieapp.user.exceptions.UserAlreadyExistException;
import com.maja.sdamovieapp.user.mapper.RegisterUserMapper;
import com.maja.sdamovieapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RegisterUserMapper registerUserMapper;

    @Transactional
    public void signup(RegisterRequestDTO requestDTO) {

        userRepository.findUserByLoginOrEmail(requestDTO.getLogin(), requestDTO.getEmail())
                .ifPresent(user -> {
                    if (user.getEmail().equals(requestDTO.getEmail()) || user.getLogin().equals(requestDTO.getLogin())) {
                        log.warn(ErrorCode.INVALID_INPUT_REGISTER_CREDENTIALS.internalCode);
                        throw new UserAlreadyExistException(ErrorCode.INVALID_INPUT_REGISTER_CREDENTIALS.internalCode);
                    }
                });

        User mappedUser = registerUserMapper.mapToUser(requestDTO);
        mappedUser.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        mappedUser.setClientType(ClientTypeEnum.STANDARD);
        mappedUser.setRole(RoleNameEnum.ROLE_USER);
        mappedUser.setActive(false);
        mappedUser.setCreatedAt(now());
        userRepository.save(mappedUser);
    }
}
