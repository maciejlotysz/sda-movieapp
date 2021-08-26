package com.maja.sdamovieapp.user.mapper;

import com.maja.sdamovieapp.user.dto.RegisterRequestDTO;
import com.maja.sdamovieapp.user.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface RegisterUserMapper {

    RegisterRequestDTO mapToRegisterDto(User user);

    User mapToUser(RegisterRequestDTO registerRequestDTO);
}
