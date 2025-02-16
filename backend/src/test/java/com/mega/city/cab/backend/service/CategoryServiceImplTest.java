package com.mega.city.cab.backend.service;

import com.mega.city.cab.backend.entity.Category;
import com.mega.city.cab.backend.repo.CategoryRepo;
import com.mega.city.cab.backend.service.CategoryDetailsService;
import com.mega.city.cab.backend.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock
    private CategoryRepo categoryRepo;

    @Mock
    private CategoryDetailsService categoryDetailsService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category(1L, "Sedan", "1");
    }

    @Test
    void testSaveCategory_NewCategory() {
        // Arrange
        when(categoryRepo.findByCategory("Sedan")).thenReturn(null);
        when(categoryRepo.save(any(Category.class))).thenReturn(category);

        // Act
        categoryService.saveCategory("Sedan", 1L);

        // Assert
        verify(categoryRepo, times(1)).findByCategory("Sedan");
        verify(categoryRepo, times(1)).save(any(Category.class));
        verify(categoryDetailsService, times(1)).saveCategoryDetails(1L, 1L);
    }

    @Test
    void testSaveCategory_ExistingCategory() {
        // Arrange
        when(categoryRepo.findByCategory("Sedan")).thenReturn(category);

        // Act
        categoryService.saveCategory("Sedan", 1L);

        // Assert
        verify(categoryRepo, times(1)).findByCategory("Sedan");
        verify(categoryRepo, never()).save(any(Category.class));
        verify(categoryDetailsService, times(1)).saveCategoryDetails(1L, 1L);
    }

    @Test
    void testUpdateCategory_NewCategory() {
        // Arrange
        when(categoryRepo.findByCategory("Sedan")).thenReturn(null);
        when(categoryRepo.save(any(Category.class))).thenReturn(category);

        // Act
        categoryService.updateCategory("Sedan", 1L);

        // Assert
        verify(categoryRepo, times(1)).findByCategory("Sedan");
        verify(categoryRepo, times(1)).save(any(Category.class));
        verify(categoryDetailsService, times(1)).updateCategoryDetails(1L, 1L);
    }

    @Test
    void testUpdateCategory_ExistingCategory() {
        // Arrange
        when(categoryRepo.findByCategory("Sedan")).thenReturn(category);

        // Act
        categoryService.updateCategory("Sedan", 1L);

        // Assert
        verify(categoryRepo, times(1)).findByCategory("Sedan");
        verify(categoryRepo, never()).save(any(Category.class));
        verify(categoryDetailsService, times(1)).updateCategoryDetails(1L, 1L);
    }

    @Test
    void testGetAllCategory_WithPermission() {
        // Arrange
        List<String> categories = Arrays.asList("Sedan", "SUV", "Hatchback");
        when(categoryRepo.getAllCategories()).thenReturn(categories);

        // Act
        List<String> result = categoryService.getAllCategory("User");

        // Assert
        assertEquals(3, result.size());
        assertTrue(result.contains("Sedan"));
        verify(categoryRepo, times(1)).getAllCategories();
    }

    @Test
    void testGetAllCategory_WithoutPermission() {
        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> {
            categoryService.getAllCategory("Admin");
        });

        verify(categoryRepo, never()).getAllCategories();
    }
}