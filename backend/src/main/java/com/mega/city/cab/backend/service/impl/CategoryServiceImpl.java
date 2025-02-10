package com.mega.city.cab.backend.service.impl;

import com.mega.city.cab.backend.entity.Category;
import com.mega.city.cab.backend.repo.CategoryRepo;
import com.mega.city.cab.backend.service.CategoryDetailsService;
import com.mega.city.cab.backend.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CategoryDetailsService categoryDetailsService;

    @Override
    public void saveCategory(String categoryName,long vehicleId) {
        Category byCategory = categoryRepo.findByCategory(categoryName);
        if (Objects.equals(byCategory,null)){
            Category category = new Category(
                    (long)0,
                    categoryName,
                    "1"
            );
            Category save = categoryRepo.save(category);
            categoryDetailsService.saveCategoryDetails(save.getCategoryId(),vehicleId);
        }
        else {
            categoryDetailsService.saveCategoryDetails(byCategory.getCategoryId(),vehicleId);
        }
    }

    @Override
    public void updateCategory(String categoryName, long vehicleId) {
        Category byCategory = categoryRepo.findByCategory(categoryName);
        if (Objects.equals(byCategory,null)){
            Category category = new Category(
                    (long)0,
                    categoryName,
                    "1"
            );
            Category save = categoryRepo.save(category);
            categoryDetailsService.updateCategoryDetails(save.getCategoryId(),vehicleId);
        }
        else {
            categoryDetailsService.updateCategoryDetails(byCategory.getCategoryId(),vehicleId);
        }
    }

    @Override
    public List<String> getAllCategory(String type) {
        if (!type.equals("User")){
            throw new BadCredentialsException("dont have permission");
        }
        return  categoryRepo.getAllCategories();
    }
}
