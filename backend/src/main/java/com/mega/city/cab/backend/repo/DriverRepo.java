package com.mega.city.cab.backend.repo;

import com.mega.city.cab.backend.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Repository
public interface DriverRepo  extends JpaRepository<Driver,Long>{

    @Query(value = "select * from driver where driver_id=:driverId and  status='1'",nativeQuery = true)
    Driver getDriverById(@Param("driverId")Long driverId);

    @Query(value = "select * from driver where email=:email and  status='1' ",nativeQuery = true)
    Driver getDriverByEmail(@Param("email")String email);

    @Query(value = "select * from driver where contact_number = :contactNumber  and  status='1' ",nativeQuery = true)
    Driver getDriverByContactNumber(@Param("contactNumber")String contactNumber);

    @Query(value = "select * from driver where license_number = :licenseNumber  and  status='1' ",nativeQuery = true)
    Driver getDriverByLicenseNumberNumber(@Param("licenseNumber")String licenseNumber);

    @Query(value = "select * from driver where nic = :nic  and  status='1' ",nativeQuery = true)
    Driver getDriverByNic(@Param("nic")String nic);

    @Query(value = "select * from driver where status='1' order by driver_id desc",nativeQuery = true)
    List<Driver> getAllDriver();

}
