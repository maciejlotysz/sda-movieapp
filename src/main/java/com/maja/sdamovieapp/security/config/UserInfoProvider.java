package com.maja.sdamovieapp.security.config;

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
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserInfoProvider implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByEmail(email);

        return userOptional
                .filter(User::isActive)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("Username with email %s not found", email)));
    }

    private UserInfo map(User user) {
        return new UserInfo(user.getId(),user.getEmail(), user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().name())));
    }
}
