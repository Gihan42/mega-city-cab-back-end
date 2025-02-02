package com.mega.city.cab.backend.repo;

import com.mega.city.cab.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo  extends JpaRepository<Category,Long> {

    @Query(value = "select * from category where category=:category",nativeQuery = true)
    Category findByCategory(@Param("category") String category);
}
