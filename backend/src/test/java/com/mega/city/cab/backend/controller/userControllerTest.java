package com.mega.city.cab.backend.controller;

import com.mega.city.cab.backend.dto.AuthenticationRequestDTO;
import com.mega.city.cab.backend.dto.UserPasswordDto;
import com.mega.city.cab.backend.dto.userDto;
import com.mega.city.cab.backend.entity.User;
import com.mega.city.cab.backend.service.userService;
import com.mega.city.cab.backend.util.response.LoginResponse;
import com.mega.city.cab.backend.util.response.StandardResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class userControllerTest {

    @InjectMocks
    private userController userController;

    @Mock
    private userService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUserLogin() {
        AuthenticationRequestDTO dto = new AuthenticationRequestDTO();
        dto.setEmail("testUser");
        dto.setPassword("testPassword");

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage("Login successful");

        when(userService.logUser(dto)).thenReturn(loginResponse);

        ResponseEntity<LoginResponse> response = userController.userLogin(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login successful", response.getBody().getMessage());
        verify(userService, times(1)).logUser(dto);
    }

    @Test
    void testSaveUser() {
        userDto dto = new userDto();
        dto.setUsername("newUser");
        dto.setPassword("newPassword");

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage("User registered successfully");

        when(userService.saveUser(dto)).thenReturn(loginResponse);

        ResponseEntity<?> response = userController.saveUser(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User registered successfully", ((LoginResponse) response.getBody()).getMessage());
        verify(userService, times(1)).saveUser(dto);
    }

    @Test
    void testUpdateUser() {
        userDto dto = new userDto();
        dto.setUsername("updatedUser");
        dto.setPassword("updatedPassword");

        User user = new User();
        user.setUsername("updatedUser");

        when(userService.updateUser(dto, "admin")).thenReturn(user);

        ResponseEntity<StandardResponse> response = userController.updateUser(dto, "admin");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User Updated", response.getBody().getMessage());
        assertEquals(user, response.getBody().getData());
        verify(userService, times(1)).updateUser(dto, "admin");
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;
        User user = new User();
        user.setUserId(userId);

        when(userService.deleteUser(userId, "admin")).thenReturn(user);

        ResponseEntity<StandardResponse> response = userController.deleteUser(userId, "admin");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User Deleted", response.getBody().getMessage());
        assertEquals(user, response.getBody().getData());
        verify(userService, times(1)).deleteUser(userId, "admin");
    }

    @Test
    void testGetAllUser() {
        userDto user1 = new userDto();
        user1.setUsername("user1");
        userDto user2 = new userDto();
        user2.setUsername("user2");

        List<userDto> userList = Arrays.asList(user1, user2);

        when(userService.getAllUser("admin")).thenReturn(userList);

        ResponseEntity<StandardResponse> response = userController.getAllUser("admin");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("all users", response.getBody().getMessage());
        assertEquals(userList, response.getBody().getData());
        verify(userService, times(1)).getAllUser("admin");
    }

    @Test
    void testGetAllAdmin() {
        userDto admin1 = new userDto();
        admin1.setUsername("admin1");
        userDto admin2 = new userDto();
        admin2.setUsername("admin2");

        List<userDto> adminList = Arrays.asList(admin1, admin2);

        when(userService.getAllAdmin("admin")).thenReturn(adminList);

        ResponseEntity<StandardResponse> response = userController.getAllAdmin("admin");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("all admins", response.getBody().getMessage());
        assertEquals(adminList, response.getBody().getData());
        verify(userService, times(1)).getAllAdmin("admin");
    }

    @Test
    void testCountUser() {
        int userCount = 10;

        when(userService.getUserCount("admin")).thenReturn(userCount);

        ResponseEntity<StandardResponse> response = userController.countUser("admin");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User Count", response.getBody().getMessage());
        assertEquals(userCount, response.getBody().getData());
        verify(userService, times(1)).getUserCount("admin");
    }

    @Test
    void testUpdateUserPassword() {
        UserPasswordDto dto = new UserPasswordDto();
        dto.setUserId(1L);
        dto.setNewPassword("newPassword");

        when(userService.updateUserPassword(dto, "admin")).thenReturn("Password updated successfully");

        ResponseEntity<StandardResponse> response = userController.updateUserPassword(dto, "admin");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password Updated", response.getBody().getMessage());
        assertEquals("Password updated successfully", response.getBody().getData());
        verify(userService, times(1)).updateUserPassword(dto, "admin");
    }

    @Test
    void testCheckUserContact() {
        Long userId = 1L;

        when(userService.checkUserContact(userId, "admin")).thenReturn(true);

        ResponseEntity<StandardResponse> response = userController.checkUserContact(userId, "admin");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("response is", response.getBody().getMessage());
        assertEquals(true, response.getBody().getData());
        verify(userService, times(1)).checkUserContact(userId, "admin");
    }

    @Test
    void testGetUserById() {
        Long uId = 1L;
        User user = new User();
        user.setUserId(uId);

        when(userService.getUserByUserId(uId, "admin")).thenReturn(user);

        ResponseEntity<StandardResponse> response = userController.getUserById(uId, "admin");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("get user", response.getBody().getMessage());
        assertEquals(user, response.getBody().getData());
        verify(userService, times(1)).getUserByUserId(uId, "admin");
    }
}