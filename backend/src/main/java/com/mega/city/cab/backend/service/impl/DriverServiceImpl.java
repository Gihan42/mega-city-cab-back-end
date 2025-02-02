package com.mega.city.cab.backend.service.impl;

import com.mega.city.cab.backend.dto.DriverDto;
import com.mega.city.cab.backend.dto.userDto;
import com.mega.city.cab.backend.entity.Driver;
import com.mega.city.cab.backend.repo.DriverRepo;
import com.mega.city.cab.backend.service.DriverService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;
import java.util.NoSuchElementException;
import java.util.Objects;


@Service
@Transactional
public class DriverServiceImpl implements DriverService {
    @Autowired
    DriverRepo driverRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Driver saveDriver(DriverDto dto, String type) {
        if (!type.equals("Admin")){
            throw new BadCredentialsException("dont have permission");
        }
        Driver driverByEmail = driverRepo.getDriverByEmail(dto.getEmail());
        Driver driverByNic = driverRepo.getDriverByNic(dto.getNic());
        Driver driverByLicenseNumberNumber = driverRepo.getDriverByLicenseNumberNumber(dto.getLicenseNumber());
        Driver driverByContactNumber = driverRepo.getDriverByContactNumber(dto.getContactNumber());
        if(Objects.equals(driverByEmail,null)&&Objects.equals(driverByNic,null)&&
                Objects.equals(driverByLicenseNumberNumber,null)&&
                Objects.equals(driverByContactNumber,null)){
            Driver map = modelMapper.map(dto, Driver.class);
            return driverRepo.save(map);
        }
        throw new RuntimeException("driver already exist");
    }

    @Override
    public Driver updateDriver(DriverDto dto, String type) {
        if (!type.equals("Admin")){
            throw new BadCredentialsException("dont have permission");
        }
        Driver driverById = driverRepo.getDriverById(dto.getDriverId());
        if(!Objects.equals(driverById,null)){
            Driver map = modelMapper.map(dto, Driver.class);
            return driverRepo.save(map);
        }
        throw new RuntimeException("driver not exist");
    }

    @Override
    public Driver deleteDriver(Long driverId, String type) {
        if (!type.equals("Admin")){
            throw new BadCredentialsException("dont have permission");
        }
        Driver driverById = driverRepo.getDriverById(driverId);
        if(!Objects.equals(driverById,null)){
            driverRepo.delete(driverById);
            return driverById;
        }
        throw new RuntimeException("driver not exist");
    }

    @Override
    public Driver getDriverById(Long driverId,String type) {
        if (!type.equals("Admin")){
            throw new BadCredentialsException("dont have permission");
        }
        Driver driverById = driverRepo.getDriverById(driverId);
        if(!Objects.equals(driverById,null)){
            return driverById;
        }
        throw new RuntimeException("driver not exist");
    }

    @Override
    public Driver getDriverByEmail(String email,String type) {
        if (!type.equals("Admin")){
            throw new BadCredentialsException("dont have permission");
        }
        Driver driverByEmail = driverRepo.getDriverByEmail(email);
        if(!Objects.equals(driverByEmail,null)){
            return driverByEmail;
        }
        throw new RuntimeException("driver not exist");
    }

    @Override
    public List<DriverDto> getAllDriver(String type) {
        if (!type.equals("Admin")){
            throw new BadCredentialsException("dont have permission");
        }
        return modelMapper.map(driverRepo.getAllDriver(),new TypeToken<List<DriverDto>>() {}.getType());
    }

    @Override
    public Driver getRandomlyDriver(String type) {
        if (!type.equals("User") && !type.equals("Admin")){
            throw new BadCredentialsException("dont have permission");
        }
        List<Driver> availableDriver = driverRepo.getAvailableDriver();
        if(availableDriver.isEmpty()){
            throw new NoSuchElementException("not have available drivers");

        }
        Random random = new Random();
        return availableDriver.get(random.nextInt(availableDriver.size()));
    }

    @Override
    public int getDriverCount(String type) {
        if (!type.equals("Admin")){
            throw new BadCredentialsException("dont have permission");
        }
       return driverRepo.getAvailableDriverCount();
    }

}
