package com.mega.city.cab.backend.service;

public interface CategoryService {
    void saveCategory(String categoryName,long vehicleId);
    void updateCategory(String categoryName,long vehicleId);
}
