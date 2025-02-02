package com.mega.city.cab.backend.service;

public interface CategoryDetailsService {

    void saveCategoryDetails(long categoryId,long vehicleId);
    void updateCategoryDetails(long categoryId,long vehicleId);
    boolean deleteCategoryDetails(long vehicleId);
}
