package com.mega.city.cab.backend.repo;

import com.mega.city.cab.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo  extends JpaRepository<Category,Long> {

    @Query(value = "select * from category where category=:category",nativeQuery = true)
    Category findByCategory(@Param("category") String category);

    @Query(value = "select distinct  category from category",nativeQuery = true)
    List<String> getAllCategories();
}
