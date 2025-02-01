package com.mega.city.cab.backend.service;

import com.mega.city.cab.backend.dto.DriverDto;
import com.mega.city.cab.backend.entity.Driver;

import java.util.List;

public interface DriverService {

    Driver saveDriver(DriverDto dto, String type);
    Driver updateDriver(DriverDto dto, String type);
    Driver deleteDriver(Long driverId,String type);
    Driver getDriverById(Long driverId,String type);
    Driver getDriverByEmail(String email,String type);
    List<DriverDto> getAllDriver(String type);
}
