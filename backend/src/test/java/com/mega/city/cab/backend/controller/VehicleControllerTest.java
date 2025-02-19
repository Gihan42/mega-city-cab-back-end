package com.mega.city.cab.backend.controller;

import com.mega.city.cab.backend.dto.VehicleDto;
import com.mega.city.cab.backend.entity.Vehicle;
import com.mega.city.cab.backend.entity.custom.VehicleCustomResult;
import com.mega.city.cab.backend.service.VehicleService;
import com.mega.city.cab.backend.util.response.StandardResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VehicleControllerTest {

    @InjectMocks
    private VehicleController vehicleController;

    @Mock
    private VehicleService vehicleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveVehicle() throws Exception {
        VehicleDto dto = new VehicleDto();
        MultipartFile imageFile = new MockMultipartFile("imageFile", "test.jpg", "image/jpeg", "test image content".getBytes());
        String type = "admin";

        Vehicle vehicle = new Vehicle();
        when(vehicleService.saveVehicle(any(VehicleDto.class), any(MultipartFile.class), anyString())).thenReturn(vehicle);

        ResponseEntity<StandardResponse> response = vehicleController.saveVehicle(dto, imageFile, type);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("vehicle saved successfully", response.getBody().getMessage());
        assertEquals(vehicle, response.getBody().getData());

        verify(vehicleService, times(1)).saveVehicle(any(VehicleDto.class), any(MultipartFile.class), anyString());
    }

    @Test
    void testUpdateVehicle() throws Exception {
        VehicleDto dto = new VehicleDto();
        MultipartFile imageFile = new MockMultipartFile("imageFile", "test.jpg", "image/jpeg", "test image content".getBytes());
        String type = "admin";

        Vehicle vehicle = new Vehicle();
        when(vehicleService.updateVehicle(any(VehicleDto.class), any(MultipartFile.class), anyString())).thenReturn(vehicle);

        ResponseEntity<StandardResponse> response = vehicleController.updateVehicle(dto, imageFile, type);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Vehicle Updated", response.getBody().getMessage());
        assertEquals(vehicle, response.getBody().getData());

        verify(vehicleService, times(1)).updateVehicle(any(VehicleDto.class), any(MultipartFile.class), anyString());
    }

    @Test
    void testGetAllVehiclesWithCategory() {
        String type = "admin";
        List<VehicleCustomResult> vehicles = Arrays.asList(
                Mockito.mock(VehicleCustomResult.class),
                Mockito.mock(VehicleCustomResult.class)
        );

        when(vehicleService.getAllVehiclesWithCategory(anyString())).thenReturn(vehicles);

        ResponseEntity<StandardResponse> response = vehicleController.getAllVehiclesWithCategory(type);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("all vehicles", response.getBody().getMessage());
        assertEquals(vehicles, response.getBody().getData());

        verify(vehicleService, times(1)).getAllVehiclesWithCategory(anyString());
    }

    @Test
    void testDeleteVehicle() {
        Long vehicleId = 1L;
        String type = "admin";
        Vehicle vehicle = new Vehicle();

        when(vehicleService.deleteVehicle(anyLong(), anyString())).thenReturn(vehicle);

        ResponseEntity<StandardResponse> response = vehicleController.deleteVehicle(vehicleId, type);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Vehicle Deleted", response.getBody().getMessage());
        assertEquals(vehicle, response.getBody().getData());

        verify(vehicleService, times(1)).deleteVehicle(anyLong(), anyString());
    }

    @Test
    void testGetVehicleModelByCategory() {
        String categoryName = "SUV";
        String type = "admin";
        List<String> models = Arrays.asList("Model1", "Model2");

        when(vehicleService.getVehicleModelByCategoryName(anyString(), anyString())).thenReturn(models);

        ResponseEntity<StandardResponse> response = vehicleController.getVehicleModelByCategory(categoryName, type);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Vehicle Model Found", response.getBody().getMessage());
        assertEquals(models, response.getBody().getData());

        verify(vehicleService, times(1)).getVehicleModelByCategoryName(anyString(), anyString());
    }

    @Test
    void testGetAllVehicleCount() {
        String type = "admin";
        int count = 10;

        when(vehicleService.getVehicleCount(anyString())).thenReturn(count);

        ResponseEntity<StandardResponse> response = vehicleController.getAllVehicleCount(type);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Vehicle Count", response.getBody().getMessage());
        assertEquals(count, response.getBody().getData());

        verify(vehicleService, times(1)).getVehicleCount(anyString());
    }

    @Test
    void testGetRandomVehicle() {
        String model = "Model1";
        String type = "admin";
        Vehicle vehicle = new Vehicle();

        when(vehicleService.randomlyGetVehicle(anyString(), anyString())).thenReturn(vehicle);

        ResponseEntity<StandardResponse> response = vehicleController.getRandomVehicle(model, type);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Vehicle Found", response.getBody().getMessage());
        assertEquals(vehicle, response.getBody().getData());

        verify(vehicleService, times(1)).randomlyGetVehicle(anyString(), anyString());
    }

    @Test
    void testGetAllVehicleModel() {
        String type = "admin";
        List<String> models = Arrays.asList("Model1", "Model2");

        when(vehicleService.getAllVehicleModel(anyString())).thenReturn(models);

        ResponseEntity<StandardResponse> response = vehicleController.getAllVehicleModel(type);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("get all vehicle models", response.getBody().getMessage());
        assertEquals(models, response.getBody().getData());

        verify(vehicleService, times(1)).getAllVehicleModel(anyString());
    }
}
