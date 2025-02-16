package com.mega.city.cab.backend.service;

import com.mega.city.cab.backend.entity.UserRoleDetails;
import com.mega.city.cab.backend.service.impl.userRoleDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import  static org.mockito.Mockito.*;

import com.mega.city.cab.backend.entity.UserRoleDetails;
import com.mega.city.cab.backend.repo.userRoleDetailsRepo;


@ExtendWith(MockitoExtension.class)
class UserRoleDetailsServiceImplTest {

    @Mock
    private userRoleDetailsRepo userRoleDetailsRepo;

    @InjectMocks
    private userRoleDetailsServiceImpl userRoleDetailsService;

    @BeforeEach
    void setUp() {
        userRoleDetailsService = new userRoleDetailsServiceImpl();
        userRoleDetailsService.userRoleDetailsRepo = userRoleDetailsRepo;
    }

    @Test
    void saveUserRoleDetails_ValidUserRoleDetails_SavesSuccessfully() {
        long userId = 1L;
        long roleId = 2L;

        UserRoleDetails userRoleDetails = new UserRoleDetails(0L, roleId, userId, "1");

        userRoleDetailsService.saveUserRoleDetails(userId, roleId);

        verify(userRoleDetailsRepo, times(1)).save(any(UserRoleDetails.class));
    }

    @Test
    void deleteUserRoleDetails_ValidUserId_DeletesSuccessfully() {
        long userId = 1L;

        UserRoleDetails role1 = new UserRoleDetails(1L, 2L, userId, "1");
        UserRoleDetails role2 = new UserRoleDetails(2L, 3L, userId, "1");
        List<UserRoleDetails> userRoles = Arrays.asList(role1, role2);

        when(userRoleDetailsRepo.getAllUserRoleByUserId(userId)).thenReturn(userRoles);
        when(userRoleDetailsRepo.getUserRoleDetailsById(anyLong())).thenAnswer(invocation -> {
            long id = invocation.getArgument(0);
            return userRoles.stream().filter(r -> r.getUserRoleDetailsId() == id).findFirst().orElse(null);
        });

        userRoleDetailsService.deleteUserRoleDetails(userId);

        verify(userRoleDetailsRepo, times(2)).delete(any(UserRoleDetails.class));
    }

    @Test
    void deleteUserRoleDetails_InvalidUserId_ThrowsException() {
        long userId = 1L;

        when(userRoleDetailsRepo.getAllUserRoleByUserId(userId)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> userRoleDetailsService.deleteUserRoleDetails(userId));
    }
}