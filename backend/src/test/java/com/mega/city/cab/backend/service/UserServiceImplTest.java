package com.mega.city.cab.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.mega.city.cab.backend.dto.AuthenticationRequestDTO;
import com.mega.city.cab.backend.dto.UserPasswordDto;
import com.mega.city.cab.backend.dto.userDto;
import com.mega.city.cab.backend.entity.User;
import com.mega.city.cab.backend.entity.UserRoles;
import com.mega.city.cab.backend.repo.UserRepo;
import com.mega.city.cab.backend.repo.UserRolesRepo;
import com.mega.city.cab.backend.repo.userRoleDetailsRepo;
import com.mega.city.cab.backend.service.impl.userServiceImpl;
import com.mega.city.cab.backend.util.JwtUtil;
import com.mega.city.cab.backend.util.response.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class UserServiceImplTest {

    @InjectMocks
    private userServiceImpl userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private userRoleDetailsRepo userRoleDetailsRepo;

    @Mock
    private userRoleDetailsService userRoleDetailsService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRolesRepo userRolesRepo;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogUser_Success() {
        // Arrange
        AuthenticationRequestDTO dto = new AuthenticationRequestDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("password");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(BCrypt.hashpw("password", BCrypt.gensalt(10)));

        List<String> roles = new ArrayList<>();
        roles.add("User");

        when(userRepo.getUserByEmail(anyString())).thenReturn(user);
        when(userRoleDetailsRepo.getUserRoleByUserEmail(anyString())).thenReturn(roles);
        when(authenticationManager.authenticate(any())).thenReturn(mock(Authentication.class));
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(mock(UserDetails.class));
        when(jwtUtil.generateToken(any(), anyString())).thenReturn("jwtToken");

        // Act
        LoginResponse response = userService.logUser(dto);

        // Assert
        assertNotNull(response);
        assertEquals("Login Success", response.getMessage());
        assertEquals(200, response.getCode());
        assertEquals("jwtToken", response.getJwt());
    }

    @Test
    public void testLogUser_InvalidCredentials() {
        // Arrange
        AuthenticationRequestDTO dto = new AuthenticationRequestDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("wrongpassword");

        when(userRepo.getUserByEmail(anyString())).thenReturn(null);

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> userService.logUser(dto));
    }

    @Test
    public void testSaveUser_Success() {
        // Arrange
        userDto dto = new userDto();
        dto.setEmail("test@example.com");
        dto.setPassword("password");
        dto.setRole("User");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(BCrypt.hashpw("password", BCrypt.gensalt(10)));
        user.setUserId(1L); // Set a valid userId

        UserRoles userRole = new UserRoles();
        userRole.setUserRoleId(1L);
        userRole.setRole("User");

        when(userRepo.getUserByEmail(anyString())).thenReturn(null);
        when(modelMapper.map(any(), eq(User.class))).thenReturn(user);
        when(userRepo.save(any())).thenReturn(user); // Return the user with a valid userId
        when(userRolesRepo.findByRole(anyString())).thenReturn(userRole);
        when(authenticationManager.authenticate(any())).thenReturn(mock(Authentication.class));
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(mock(UserDetails.class));
        when(jwtUtil.generateToken(any(), anyString())).thenReturn("jwtToken");

        // Act
        LoginResponse response = userService.saveUser(dto);

        // Assert
        assertNotNull(response);
        assertEquals("User Created Successfully", response.getMessage());
        assertEquals(200, response.getCode());
        assertEquals("jwtToken", response.getJwt());
        assertEquals(1L, response.getUserId()); // Verify userId is set correctly
    }

    @Test
    public void testSaveUser_UserAlreadyExists() {
        // Arrange
        userDto dto = new userDto();
        dto.setEmail("test@example.com");
        dto.setPassword("password");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(BCrypt.hashpw("password", BCrypt.gensalt(10)));

        when(userRepo.getUserByEmail(anyString())).thenReturn(user);

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> userService.saveUser(dto));
    }

    @Test
    public void testUpdateUser_Success() {
        // Arrange
        userDto dto = new userDto();
        dto.setId(1L);
        dto.setEmail("test@example.com");
        dto.setPassword("password");

        User user = new User();
        user.setUserId(1L);
        user.setEmail("test@example.com");
        user.setPassword(BCrypt.hashpw("password", BCrypt.gensalt(10)));

        when(userRepo.getUserById(anyLong())).thenReturn(user);
        when(modelMapper.map(any(), eq(User.class))).thenReturn(user);
        when(userRepo.save(any())).thenReturn(user);

        // Act
        User updatedUser = userService.updateUser(dto, "Admin");

        // Assert
        assertNotNull(updatedUser);
        assertEquals(1L, updatedUser.getUserId());
    }

    @Test
    public void testUpdateUser_NoPermission() {
        // Arrange
        userDto dto = new userDto();
        dto.setId(1L);
        dto.setEmail("test@example.com");
        dto.setPassword("password");

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> userService.updateUser(dto, "Guest"));
    }

    @Test
    public void testDeleteUser_Success() {
        // Arrange
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);

        when(userRepo.getUserById(anyLong())).thenReturn(user);
        doNothing().when(userRoleDetailsService).deleteUserRoleDetails(anyLong());
        doNothing().when(userRepo).delete(any());

        // Act
        User deletedUser = userService.deleteUser(userId, "Admin");

        // Assert
        assertNotNull(deletedUser);
        assertEquals(userId, deletedUser.getUserId());
    }

    @Test
    public void testDeleteUser_NoPermission() {
        // Arrange
        long userId = 1L;

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> userService.deleteUser(userId, "Guest"));
    }

    @Test
    public void testGetAllUser_Success() {
        // Arrange
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setUserId(1L);
        user1.setEmail("user1@example.com");
        users.add(user1);

        User user2 = new User();
        user2.setUserId(2L);
        user2.setEmail("user2@example.com");
        users.add(user2);

        List<userDto> userDtos = new ArrayList<>();
        userDto dto1 = new userDto();
        dto1.setId(1L);
        dto1.setEmail("user1@example.com");
        userDtos.add(dto1);

        userDto dto2 = new userDto();
        dto2.setId(2L);
        dto2.setEmail("user2@example.com");
        userDtos.add(dto2);

        when(userRepo.getAllUserByUser()).thenReturn(users); // Mock to return a list of users
        when(modelMapper.map(users, new TypeToken<List<userDto>>() {}.getType())).thenReturn(userDtos); // Mock the ModelMapper

        // Act
        List<userDto> result = userService.getAllUser("Admin");

        // Assert
        assertNotNull(result); // Ensure the result is not null
        assertEquals(2, result.size()); // Verify the size of the returned list
    }


    @Test
    public void testGetAllUser_NoPermission() {
        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> userService.getAllUser("Guest"));
    }

    @Test
    public void testGetUserCount_Success() {
        // Arrange
        when(userRepo.getTotalUser()).thenReturn(10);

        // Act
        int count = userService.getUserCount("Admin");

        // Assert
        assertEquals(10, count);
    }

    @Test
    public void testGetUserCount_NoPermission() {
        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> userService.getUserCount("User"));
    }

    @Test
    public void testUpdateUserPassword_Success() {
        // Arrange
        UserPasswordDto dto = new UserPasswordDto();
        dto.setUserId(1L);
        dto.setCurrentPassword("oldPassword");
        dto.setNewPassword("newPassword");

        User user = new User();
        user.setUserId(1L);
        user.setPassword(BCrypt.hashpw("oldPassword", BCrypt.gensalt(10)));

        when(userRepo.getUserById(anyLong())).thenReturn(user);
        when(userRepo.save(any())).thenReturn(user);

        // Act
        String result = userService.updateUserPassword(dto, "Admin");

        // Assert
        assertEquals("Password Updated Successfully", result);
    }

    @Test
    public void testUpdateUserPassword_WrongCurrentPassword() {
        // Arrange
        UserPasswordDto dto = new UserPasswordDto();
        dto.setUserId(1L);
        dto.setCurrentPassword("wrongPassword");
        dto.setNewPassword("newPassword");

        User user = new User();
        user.setUserId(1L);
        user.setPassword(BCrypt.hashpw("oldPassword", BCrypt.gensalt(10)));

        when(userRepo.getUserById(anyLong())).thenReturn(user);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.updateUserPassword(dto, "Admin"));
    }

    @Test
    public void testCheckUserContact_Success() {
        // Arrange
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        user.setContactNumber("1234567890");

        when(userRepo.getUserById(anyLong())).thenReturn(user);

        // Act
        boolean result = userService.checkUserContact(userId, "Admin");

        // Assert
        assertTrue(result);
    }

    @Test
    public void testCheckUserContact_NoContact() {
        // Arrange
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        user.setContactNumber("");

        when(userRepo.getUserById(anyLong())).thenReturn(user);

        // Act
        boolean result = userService.checkUserContact(userId, "Admin");

        // Assert
        assertFalse(result);
    }

    @Test
    public void testGetAllAdmin_Success() {
        // Arrange
        List<User> admins = new ArrayList<>();
        User admin1 = new User();
        admin1.setUserId(1L);
        admin1.setEmail("admin1@example.com");
        admins.add(admin1);

        User admin2 = new User();
        admin2.setUserId(2L);
        admin2.setEmail("admin2@example.com");
        admins.add(admin2);

        List<userDto> adminDtos = new ArrayList<>();
        userDto dto1 = new userDto();
        dto1.setId(1L);
        dto1.setEmail("admin1@example.com");
        adminDtos.add(dto1);

        userDto dto2 = new userDto();
        dto2.setId(2L);
        dto2.setEmail("admin2@example.com");
        adminDtos.add(dto2);

        when(userRepo.getAllAdmins()).thenReturn(admins); // Mock to return a list of admins
        when(modelMapper.map(admins, new TypeToken<List<userDto>>() {}.getType())).thenReturn(adminDtos); // Mock the ModelMapper

        // Act
        List<userDto> result = userService.getAllAdmin("Admin");

        // Assert
        assertNotNull(result); // Ensure the result is not null
        assertEquals(2, result.size()); // Verify the size of the returned list
    }

    @Test
    public void testGetAllAdmin_NoPermission() {
        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> userService.getAllAdmin("User"));
    }

    @Test
    public void testGetUserByUserId_Success() {
        // Arrange
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);

        when(userRepo.getUserById(anyLong())).thenReturn(user);

        // Act
        User result = userService.getUserByUserId(userId, "User");

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
    }

    @Test
    public void testGetUserByUserId_NoPermission() {
        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> userService.getUserByUserId(1L, "Admin"));
    }
}