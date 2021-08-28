package com.maja.sdamovieapp.security.config;

import com.maja.sdamovieapp.application.constants.ErrorCode;
import com.maja.sdamovieapp.user.entity.User;
import com.maja.sdamovieapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserInfoProvider implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
                .filter(User::isActive)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorCode.USER_NOT_FOUND.internalCode));
    }

    private UserInfo map(User user) {
        return new UserInfo(user.getId(),user.getEmail(), user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().name())));
    }
}
