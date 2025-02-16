package com.mega.city.cab.backend.service;


import com.mega.city.cab.backend.entity.CategoryDetails;
import com.mega.city.cab.backend.repo.CategoryDetailsRepo;
import com.mega.city.cab.backend.service.impl.CategoryDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryDetailsServiceImplTest {

    @Mock
    private CategoryDetailsRepo categoryDetailsRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CategoryDetailsServiceImpl categoryDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCategoryDetails() {
        // Arrange
        long categoryId = 1L;
        long vehicleId = 1L;
        CategoryDetails categoryDetails = new CategoryDetails(0L, categoryId, vehicleId, "1");

        // Act
        categoryDetailsService.saveCategoryDetails(categoryId, vehicleId);

        // Assert
        verify(categoryDetailsRepo, times(1)).save(categoryDetails);
    }

    @Test
    void testUpdateCategoryDetails() {
        // Arrange
        long categoryId = 1L;
        long vehicleId = 1L;
        CategoryDetails existingDetails = new CategoryDetails(1L, 2L, vehicleId, "1");
        CategoryDetails newDetails = new CategoryDetails(0L, categoryId, vehicleId, "1");

        when(categoryDetailsRepo.findByCategoryDetailsVehicleId(vehicleId)).thenReturn(existingDetails);

        // Act
        categoryDetailsService.updateCategoryDetails(categoryId, vehicleId);

        // Assert
        verify(categoryDetailsRepo, times(1)).delete(existingDetails);
        verify(categoryDetailsRepo, times(1)).save(newDetails);
    }

    @Test
    void testUpdateCategoryDetails_WhenNoExistingDetails() {
        // Arrange
        long categoryId = 1L;
        long vehicleId = 1L;

        when(categoryDetailsRepo.findByCategoryDetailsVehicleId(vehicleId)).thenReturn(null);

        // Act
        categoryDetailsService.updateCategoryDetails(categoryId, vehicleId);

        // Assert
        verify(categoryDetailsRepo, never()).delete(any());
        verify(categoryDetailsRepo, never()).save(any());
    }

    @Test
    void testDeleteCategoryDetails() {
        // Arrange
        long vehicleId = 1L;
        CategoryDetails categoryDetails = new CategoryDetails(1L, 2L, vehicleId, "1");

        when(categoryDetailsRepo.findByCategoryDetailsVehicleId(vehicleId)).thenReturn(categoryDetails);

        // Act
        boolean result = categoryDetailsService.deleteCategoryDetails(vehicleId);

        // Assert
        assertTrue(result);
        verify(categoryDetailsRepo, times(1)).delete(categoryDetails);
    }

    @Test
    void testDeleteCategoryDetails_WhenNoDetailsFound() {
        // Arrange
        long vehicleId = 1L;

        when(categoryDetailsRepo.findByCategoryDetailsVehicleId(vehicleId)).thenReturn(null);

        // Act
        boolean result = categoryDetailsService.deleteCategoryDetails(vehicleId);

        // Assert
        assertFalse(result);
        verify(categoryDetailsRepo, never()).delete(any());
    }
}