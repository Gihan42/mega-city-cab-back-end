package com.mega.city.cab.backend.controller;

import com.mega.city.cab.backend.service.CategoryService;
import com.mega.city.cab.backend.util.response.StandardResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private List<String> mockCategories;

    @BeforeEach
    public void setUp() {
        mockCategories = Arrays.asList("Category1", "Category2", "Category3");
    }

    @Test
    public void testGetAllCategories() {
        // Arrange
        String type = "someType";
        when(categoryService.getAllCategory(type)).thenReturn(mockCategories);

        // Act
        ResponseEntity<StandardResponse> response = categoryController.getAllCategories(type);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getCode());
        assertEquals("all categories", response.getBody().getMessage());
        assertEquals(mockCategories, response.getBody().getData());
    }
}