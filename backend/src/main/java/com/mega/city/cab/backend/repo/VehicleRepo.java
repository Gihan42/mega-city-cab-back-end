package com.mega.city.cab.backend.repo;


import com.mega.city.cab.backend.entity.Vehicle;
import com.mega.city.cab.backend.entity.custom.VehicleCustomResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle,Long> {

    @Query(value = "select * from vehicle where plate_number=:plateNumber",nativeQuery = true)
    Vehicle findByPlateNumber(@Param("plateNumber") String plateNumber);

    @Query(value = "select * from vehicle where vehicle_id=:vehicleId",nativeQuery = true)
    Vehicle findByVehicleId(@Param("vehicleId") long vehicleId);

    @Query(value = "select  v.vehicle_id as VehicleId, v.plate_number as PlateNumber , v.passenger_count as PassengerCount, v.price_per_km as  PricePerKm, v.vehicle_model as Model,v.status as vehicleStatus, v.image as Image,c.category as Category,c.status as categoryStatus from vehicle v left join category_details cd on v.vehicle_id = cd.vehicle_id left join category c on cd.category_id = c.category_id where v.status='Available' order by  v.vehicle_id desc",nativeQuery = true)
    List<VehicleCustomResult> getAllVehiclesWithCategory();

    @Query(value = "select v.vehicle_model from vehicle v join category_details cd on v.vehicle_id = cd.vehicle_id join category c on cd.category_id = c.category_id  where c.category = :categoryName and c.status ='1'",nativeQuery = true)
    List<String> getVehicleModelByCategoryName(@Param("categoryName") String categoryName);

    @Query(value = "select count(*) from vehicle",nativeQuery = true)
    int getVehicleCount();

    @Query(value = "select * from vehicle where vehicle_model=:vehicleModel  and status='Available'",nativeQuery = true)
    List<Vehicle> findByVehicleModel(@Param("vehicleModel") String vehicleModel);

}
