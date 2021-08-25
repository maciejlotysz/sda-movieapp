package com.maja.sdamovieapp.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] ADMIN_PATHS = {};
    private static final String[] SWAGGER_PATHS = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/swagger-ui/index.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui/",
            "/swagger-ui"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/api/v1/movie/**").permitAll()
                    .antMatchers("/api/v1/movieapp").permitAll()
                    .antMatchers("/api/v1/auth/**").permitAll()
                    .antMatchers("/api/v1/signin").permitAll()
                    .antMatchers(SWAGGER_PATHS).permitAll()
                    .antMatchers(ADMIN_PATHS).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .cors()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
