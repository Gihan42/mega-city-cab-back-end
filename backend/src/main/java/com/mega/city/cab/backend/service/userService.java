package com.mega.city.cab.backend.service;


import com.mega.city.cab.backend.dto.AuthenticationRequestDTO;
import com.mega.city.cab.backend.dto.userDto;
import com.mega.city.cab.backend.entity.User;
import com.mega.city.cab.backend.util.response.LoginResponse;

public interface userService {
    LoginResponse logUser(AuthenticationRequestDTO dto);
    LoginResponse saveUser(userDto dto);
    User updateUser(userDto dto, String type);
    User deleteUser(Long userId,String type);
}

