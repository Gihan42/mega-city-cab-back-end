package com.mega.city.cab.backend.controller;

import com.mega.city.cab.backend.dto.DriverDto;
import com.mega.city.cab.backend.dto.userDto;
import com.mega.city.cab.backend.entity.Driver;
import com.mega.city.cab.backend.repo.DriverRepo;
import com.mega.city.cab.backend.service.DriverService;
import com.mega.city.cab.backend.util.response.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/driver")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class driverController {

    @Autowired
    DriverService driverService;

//    save driver endpoint
    @PostMapping(path = "/save")
    public ResponseEntity<StandardResponse> saveDriver(
            @RequestBody DriverDto dto,
            @RequestAttribute String type){
        Driver driver = driverService.saveDriver(dto, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"Driver Saved",driver),
                HttpStatus.OK
        );
    }

//    update driver endpoint
    @PutMapping(path = "/update")
    public ResponseEntity<StandardResponse> updateDriver(@RequestBody DriverDto dto,
                                                       @RequestAttribute String type){
        Driver driver = driverService.updateDriver(dto, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"Driver Updated",driver),
                HttpStatus.OK
        );

    }
//    delete driver endpoint
    @DeleteMapping(params = {"driverId"})
    public ResponseEntity<StandardResponse> deleteDriver(@RequestParam long driverId,
                                                         @RequestAttribute String type
                                                         ){
        Driver driver = driverService.deleteDriver(driverId, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"Driver Deleted",driver),
                HttpStatus.OK
        );

    }

//    get driver by driver id endpoint
    @GetMapping(params = {"driverId"})
    public  ResponseEntity<StandardResponse> getDriverById(@RequestParam long driverId,
                                                           @RequestAttribute String type){
        Driver driver = driverService.getDriverById(driverId, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"Driver Found",driver),
                HttpStatus.OK
        );

    }

//    get driver by driver email endpoint
    @GetMapping(params = {"email"})
    public ResponseEntity<StandardResponse> getDriverByEmail(@RequestParam String email,
                                                             @RequestAttribute String type){
        Driver driverByEmail = driverService.getDriverByEmail(email, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"Driver Found",driverByEmail),
                HttpStatus.OK
        );
    }

//    get all drivers
    @GetMapping(path = "allDrivers")
    public ResponseEntity<StandardResponse> getAllDriver(@RequestAttribute String type){
        List<DriverDto> allDriver = driverService.getAllDriver(type);
        return new ResponseEntity<>(
                new StandardResponse(200,"all drivers",allDriver),
                HttpStatus.OK
        );
    }


}
