package com.mega.city.cab.backend.service;

import java.util.List;

public interface CategoryService {
    void saveCategory(String categoryName,long vehicleId);
    void updateCategory(String categoryName,long vehicleId);
    List<String> getAllCategory(String type);
}
