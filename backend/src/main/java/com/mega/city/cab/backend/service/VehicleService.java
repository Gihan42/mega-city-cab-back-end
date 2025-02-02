package com.mega.city.cab.backend.service;

import com.mega.city.cab.backend.dto.VehicleDto;
import com.mega.city.cab.backend.entity.Vehicle;
import com.mega.city.cab.backend.entity.custom.VehicleCustomResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VehicleService {


    Vehicle saveVehicle(VehicleDto vehicleDto, MultipartFile file,String type) ;
    Vehicle updateVehicle(VehicleDto vehicleDto, MultipartFile file,String type);
    List<VehicleCustomResult> getAllVehiclesWithCategory(String type);
    Vehicle deleteVehicle(long vehicleId,String type);
    List<String> getVehicleModelByCategoryName(String categoryName,String type);
    int getVehicleCount(String type);
    Vehicle randomlyGetVehicle(String ModelName,String type);
}
