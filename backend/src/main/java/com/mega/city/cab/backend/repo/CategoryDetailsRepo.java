package com.mega.city.cab.backend.repo;


import com.mega.city.cab.backend.entity.CategoryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryDetailsRepo extends JpaRepository<CategoryDetails,Long> {

    @Query(value = "select * from category_details where category_id = :categoryId and status='1'",nativeQuery = true)
    List<CategoryDetails> findByCategoryId( @Param("categoryId") long categoryId);

    @Query(value = "select * from category_details where category_details_id=:categoryDetailsId and status='1'",nativeQuery = true)
    CategoryDetails findByCategoryDetailsId(@Param("categoryDetailsId") long categoryDetailsId);

    @Query(value="select * from category_details where vehicle_id =:vehicleId and status='1'",nativeQuery = true)
    CategoryDetails findByCategoryDetailsVehicleId(@Param("vehicleId") long vehicleId);

}
