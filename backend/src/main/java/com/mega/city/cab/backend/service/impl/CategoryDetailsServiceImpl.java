package com.mega.city.cab.backend.service.impl;

import com.mega.city.cab.backend.entity.CategoryDetails;
import com.mega.city.cab.backend.repo.CategoryDetailsRepo;
import com.mega.city.cab.backend.service.CategoryDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Transactional
public class CategoryDetailsServiceImpl implements CategoryDetailsService {
    @Autowired
    CategoryDetailsRepo categoryDetailsRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public void saveCategoryDetails(long categoryId, long vehicleId) {
        CategoryDetails categoryDetails = new CategoryDetails(
                (long)0,
                categoryId,
                vehicleId,
                "1"
        );
        categoryDetailsRepo.save(categoryDetails);
    }

    @Override
    public void updateCategoryDetails(long categoryId, long vehicleId) {
        boolean isDeleted = deleteCategoryDetails(vehicleId);
        if(isDeleted){
            CategoryDetails categoryDetails = new CategoryDetails(
                    (long)0,
                    categoryId,
                    vehicleId,
                    "1"
            );
            categoryDetailsRepo.save(categoryDetails);
        }
    }

    @Override
    public boolean deleteCategoryDetails(long vehicleId) {
        CategoryDetails byCategoryDetailsVehicleId = categoryDetailsRepo.findByCategoryDetailsVehicleId(vehicleId);
        if(!Objects.equals(byCategoryDetailsVehicleId,null)){
            categoryDetailsRepo.delete(byCategoryDetailsVehicleId);
            return true;
        }
        return false;
    }
}
