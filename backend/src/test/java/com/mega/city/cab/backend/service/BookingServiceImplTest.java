package com.mega.city.cab.backend.service;

import com.mega.city.cab.backend.dto.BookingDto;
import com.mega.city.cab.backend.entity.Booking;
import com.mega.city.cab.backend.entity.custom.CustomBookingDetails;
import com.mega.city.cab.backend.entity.custom.CustomBookingResult;
import com.mega.city.cab.backend.repo.BookingRepo;
import com.mega.city.cab.backend.service.DriverService;
import com.mega.city.cab.backend.service.VehicleService;
import com.mega.city.cab.backend.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookingServiceImplTest {

    @Mock
    private BookingRepo bookingRepo;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private VehicleService vehicleService;

    @Mock
    private DriverService driverService;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBooking_Success() {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setVehicleId(1L);
        bookingDto.setDriverId(1L);

        Booking booking = new Booking();
        booking.setStatus("Pending");

        when(modelMapper.map(bookingDto, Booking.class)).thenReturn(booking);
        when(vehicleService.changeVehicleStatus(1L)).thenReturn(true);
        when(driverService.changeStatusInDriver(1L)).thenReturn(true);
        when(bookingRepo.save(any(Booking.class))).thenReturn(booking);

        Booking result = bookingService.saveBooking(bookingDto, "User");

        assertNotNull(result);
        assertEquals("Pending", result.getStatus());
        verify(bookingRepo, times(1)).save(any(Booking.class));
    }

    @Test
    void testSaveBooking_NoPermission() {
        BookingDto bookingDto = new BookingDto();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookingService.saveBooking(bookingDto, "Admin");
        });

        assertEquals("dont have permission", exception.getMessage());
    }

    @Test
    void testUpdateBookingStatus_Success() {
        Booking booking = new Booking();
        booking.setStatus("Pending");
        booking.setVehicleId(1L);
        booking.setDriverId(1L);

        when(bookingRepo.getBookingById(1L)).thenReturn(booking);
        when(vehicleService.updateVehicleStatus(1L)).thenReturn(true);
        when(driverService.updateStatusInDriver(1L)).thenReturn(true);
        when(bookingRepo.save(any(Booking.class))).thenReturn(booking);

        Booking result = bookingService.updateBookingStatus(1L, "Admin");

        assertNotNull(result);
        assertEquals("Confirmed", result.getStatus());
        verify(bookingRepo, times(1)).save(any(Booking.class));
    }

    @Test
    void testUpdateBookingStatus_NoPermission() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookingService.updateBookingStatus(1L, "User");
        });

        assertEquals("dont have permission", exception.getMessage());
    }

    @Test
    void testGetAllBookingByCustomer_Success() {
        // Mock the CustomBookingResult object
        CustomBookingResult customBookingResult = mock(CustomBookingResult.class);

        // Create a list of mocked CustomBookingResult objects
        List<CustomBookingResult> bookingList = Arrays.asList(customBookingResult);

        // Mock the repository method
        when(bookingRepo.getBookingByCustomerId(1L)).thenReturn(bookingList);

        // Call the service method
        List<CustomBookingResult> result = bookingService.getAllBookingByCustomer(1L, "User");

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetAllBookingByCustomer_NoPermission() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookingService.getAllBookingByCustomer(1L, "Admin");
        });

        assertEquals("dont have permission", exception.getMessage());
    }

    @Test
    void testGetBookingDetails_Success() {
        // Mock the CustomBookingDetails object
        CustomBookingDetails customBookingDetails = mock(CustomBookingDetails.class);

        // Create a list of mocked CustomBookingDetails objects
        List<CustomBookingDetails> bookingDetailsList = Arrays.asList(customBookingDetails);

        // Mock the repository method
        when(bookingRepo.getBookingDetails()).thenReturn(bookingDetailsList);

        // Call the service method
        List<CustomBookingDetails> result = bookingService.getBookingDetails("Admin");

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetBookingDetails_NoPermission() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookingService.getBookingDetails("User");
        });

        assertEquals("dont have permission", exception.getMessage());
    }

    @Test
    void testGetPendingCount_Success() {
        when(bookingRepo.getPendingCount()).thenReturn(5);

        int result = bookingService.getPendingCount("Admin");

        assertEquals(5, result);
    }

    @Test
    void testGetPendingCount_NoPermission() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookingService.getPendingCount("User");
        });

        assertEquals("dont have permission", exception.getMessage());
    }
}