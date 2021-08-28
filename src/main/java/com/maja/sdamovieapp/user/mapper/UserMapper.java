package com.maja.sdamovieapp.user.mapper;

import com.maja.sdamovieapp.user.dto.RegisterRequestDTO;
import com.maja.sdamovieapp.user.dto.UserDTO;
import com.maja.sdamovieapp.user.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User mapToUser(RegisterRequestDTO registerRequestDTO);

    UserDTO mapToUserDTO(User user);
}
