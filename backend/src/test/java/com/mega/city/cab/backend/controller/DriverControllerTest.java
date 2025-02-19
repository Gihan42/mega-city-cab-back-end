package com.mega.city.cab.backend.controller;

import com.mega.city.cab.backend.dto.DriverDto;
import com.mega.city.cab.backend.entity.Driver;
import com.mega.city.cab.backend.service.DriverService;
import com.mega.city.cab.backend.util.response.StandardResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class DriverControllerTest {

    @InjectMocks
    private driverController driverController;

    @Mock
    private DriverService driverService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveDriver() {
        DriverDto driverDto = new DriverDto();
        Driver driver = new Driver();
        when(driverService.saveDriver(any(DriverDto.class), anyString())).thenReturn(driver);

        ResponseEntity<StandardResponse> response = driverController.saveDriver(driverDto, "admin");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Driver Saved", response.getBody().getMessage());
        assertEquals(driver, response.getBody().getData());
    }

    @Test
    public void testUpdateDriver() {
        DriverDto driverDto = new DriverDto();
        Driver driver = new Driver();
        when(driverService.updateDriver(any(DriverDto.class), anyString())).thenReturn(driver);

        ResponseEntity<StandardResponse> response = driverController.updateDriver(driverDto, "admin");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Driver Updated", response.getBody().getMessage());
        assertEquals(driver, response.getBody().getData());
    }

    @Test
    public void testDeleteDriver() {
        Driver driver = new Driver();
        when(driverService.deleteDriver(anyLong(), anyString())).thenReturn(driver);

        ResponseEntity<StandardResponse> response = driverController.deleteDriver(1L, "admin");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Driver Deleted", response.getBody().getMessage());
        assertEquals(driver, response.getBody().getData());
    }

    @Test
    public void testGetDriverById() {
        Driver driver = new Driver();
        when(driverService.getDriverById(anyLong(), anyString())).thenReturn(driver);

        ResponseEntity<StandardResponse> response = driverController.getDriverById(1L, "admin");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Driver Found", response.getBody().getMessage());
        assertEquals(driver, response.getBody().getData());
    }

    @Test
    public void testGetDriverByEmail() {
        Driver driver = new Driver();
        when(driverService.getDriverByEmail(anyString(), anyString())).thenReturn(driver);

        ResponseEntity<StandardResponse> response = driverController.getDriverByEmail("driver@example.com", "admin");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Driver Found", response.getBody().getMessage());
        assertEquals(driver, response.getBody().getData());
    }

    @Test
    public void testGetAllDriver() {
        List<DriverDto> drivers = Arrays.asList(new DriverDto(), new DriverDto());
        when(driverService.getAllDriver(anyString())).thenReturn(drivers);

        ResponseEntity<StandardResponse> response = driverController.getAllDriver("admin");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("all drivers", response.getBody().getMessage());
        assertEquals(drivers, response.getBody().getData());
    }

    @Test
    public void testGetRandomlyDriver() {
        Driver driver = new Driver();
        when(driverService.getRandomlyDriver(anyString())).thenReturn(driver);

        ResponseEntity<StandardResponse> response = driverController.getRandomVehicle("admin");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Driver Found", response.getBody().getMessage());
        assertEquals(driver, response.getBody().getData());
    }

    @Test
    public void testGetDriverCount() {
        int count = 5;
        when(driverService.getDriverCount(anyString())).thenReturn(count);

        ResponseEntity<StandardResponse> response = driverController.getDriverCount("admin");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Driver Count", response.getBody().getMessage());
        assertEquals(count, response.getBody().getData());
    }
}
