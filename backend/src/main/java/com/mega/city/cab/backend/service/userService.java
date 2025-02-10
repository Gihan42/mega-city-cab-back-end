package com.mega.city.cab.backend.service;


import com.mega.city.cab.backend.dto.AuthenticationRequestDTO;
import com.mega.city.cab.backend.dto.UserPasswordDto;
import com.mega.city.cab.backend.dto.userDto;
import com.mega.city.cab.backend.entity.User;
import com.mega.city.cab.backend.util.response.LoginResponse;

import java.util.List;

public interface userService {
    LoginResponse logUser(AuthenticationRequestDTO dto);
    LoginResponse saveUser(userDto dto);
    User updateUser(userDto dto, String type);
    User deleteUser(long userId,String type);
    List<userDto> getAllUser(String type);
    int getUserCount(String type);
    String updateUserPassword(UserPasswordDto dto, String type);
    boolean checkUserContact(long userId,String type);
    List<userDto> getAllAdmin(String type);
}

