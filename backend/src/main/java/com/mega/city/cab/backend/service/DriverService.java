package com.mega.city.cab.backend.service;

import com.mega.city.cab.backend.dto.DriverDto;
import com.mega.city.cab.backend.entity.Driver;

import java.util.List;

public interface DriverService {

    Driver saveDriver(DriverDto dto, String type);
    Driver updateDriver(DriverDto dto, String type);
    Driver deleteDriver(long driverId,String type);
    Driver getDriverById(long driverId,String type);
    Driver getDriverByEmail(String email,String type);
    List<DriverDto> getAllDriver(String type);
    Driver getRandomlyDriver(String type);
    int getDriverCount(String type);
    boolean changeStatusInDriver(long driverId);
    boolean updateStatusInDriver(long driverId);
}
