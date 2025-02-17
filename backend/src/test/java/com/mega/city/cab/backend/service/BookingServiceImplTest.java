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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
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
        bookingDto.setBookingDateTime(new Date());
        bookingDto.setHours("2.5");

        Booking booking = new Booking();
        booking.setStatus("Booking");

        when(bookingRepo.getAllBookingDatesByVehicleId(anyLong())).thenReturn(Collections.emptyList());
        when(modelMapper.map(bookingDto, Booking.class)).thenReturn(booking);
        when(bookingRepo.save(booking)).thenReturn(booking);

        Booking result = bookingService.saveBooking(bookingDto, "User");

        assertNotNull(result);
        assertEquals("Booking", result.getStatus());
        verify(bookingRepo, times(1)).save(booking);
    }

    @Test
    void testSaveBooking_ThrowsException_WhenTypeIsNotUser() {
        BookingDto bookingDto = new BookingDto();

        assertThrows(RuntimeException.class, () -> bookingService.saveBooking(bookingDto, "Admin"));
    }

    @Test
    void testSaveBooking_ThrowsException_WhenDateIsAlreadyBooked() {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setVehicleId(1L);
        bookingDto.setBookingDateTime(new Date());
        bookingDto.setHours("2.5");

        List<Date> bookedDates = Collections.singletonList(new Date());

        when(bookingRepo.getAllBookingDatesByVehicleId(anyLong())).thenReturn(bookedDates);

        assertThrows(RuntimeException.class, () -> bookingService.saveBooking(bookingDto, "User"));
    }

    @Test
    void testUpdateBookingStatus_Success() {
        long bookingId = 1L;
        long vehicleId = 123L; // Add a valid vehicleId
        long driverId = 456L; // Add a valid driverId

        // Create a Booking object and set required fields
        Booking booking = new Booking();
        booking.setStatus("Pending");
        booking.setVehicleId(vehicleId); // Set vehicleId
        booking.setDriverId(driverId); // Set driverId

        // Mock the behavior of dependencies
        when(bookingRepo.getBookingById(bookingId)).thenReturn(booking);
        when(vehicleService.updateVehicleStatus(vehicleId)).thenReturn(true);
        when(driverService.updateStatusInDriver(driverId)).thenReturn(true);
        when(bookingRepo.save(booking)).thenReturn(booking);

        // Call the method under test
        Booking result = bookingService.updateBookingStatus(bookingId, "Admin");

        // Assertions
        assertNotNull(result);
        assertEquals("Confirmed", result.getStatus());
        verify(bookingRepo, times(1)).save(booking);
        verify(vehicleService, times(1)).updateVehicleStatus(vehicleId);
        verify(driverService, times(1)).updateStatusInDriver(driverId);
    }

    @Test
    void testUpdateBookingStatus_ThrowsException_WhenTypeIsNotAdmin() {
        long bookingId = 1L;

        assertThrows(RuntimeException.class, () -> bookingService.updateBookingStatus(bookingId, "User"));
    }

    @Test
    void testUpdateBookingStatus_ThrowsException_WhenBookingDoesNotExist() {
        long bookingId = 1L;

        when(bookingRepo.getBookingById(bookingId)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> bookingService.updateBookingStatus(bookingId, "Admin"));
    }

    @Test
    void testGetAllBookingByCustomer_Success() {
        long userId = 1L;
        CustomBookingResult mockResult = mock(CustomBookingResult.class); // Mock the abstract class
        List<CustomBookingResult> expectedResults = Collections.singletonList(mockResult);

        when(bookingRepo.getBookingByCustomerId(userId)).thenReturn(expectedResults);

        List<CustomBookingResult> results = bookingService.getAllBookingByCustomer(userId, "User");

        assertNotNull(results);
        assertEquals(expectedResults, results);
    }

    @Test
    void testGetAllBookingByCustomer_ThrowsException_WhenTypeIsNotUser() {
        long userId = 1L;

        assertThrows(RuntimeException.class, () -> bookingService.getAllBookingByCustomer(userId, "Admin"));
    }

    @Test
    void testGetBookingDetails_Success() {
        CustomBookingDetails mockDetails = mock(CustomBookingDetails.class); // Mock the abstract class
        List<CustomBookingDetails> expectedDetails = Collections.singletonList(mockDetails);

        when(bookingRepo.getBookingDetails()).thenReturn(expectedDetails);

        List<CustomBookingDetails> details = bookingService.getBookingDetails("Admin");

        assertNotNull(details);
        assertEquals(expectedDetails, details);
    }

    @Test
    void testGetBookingDetails_ThrowsException_WhenTypeIsNotAdmin() {
        assertThrows(RuntimeException.class, () -> bookingService.getBookingDetails("User"));
    }

    @Test
    void testGetPendingCount_Success() {
        int expectedCount = 5;

        when(bookingRepo.getPendingCount()).thenReturn(expectedCount);

        int count = bookingService.getPendingCount("Admin");

        assertEquals(expectedCount, count);
    }

    @Test
    void testGetPendingCount_ThrowsException_WhenTypeIsNotAdmin() {
        assertThrows(RuntimeException.class, () -> bookingService.getPendingCount("User"));
    }

    @Test
    void testGetAllBookingDateByVehicleId_Success() {
        long vehicleId = 1L;
        List<Date> expectedDates = Collections.singletonList(new Date());

        when(bookingRepo.getAllBookingDatesByVehicleId(vehicleId)).thenReturn(expectedDates);

        List<Date> dates = bookingService.getAllBookingDateByVehicleId(vehicleId, "User");

        assertNotNull(dates);
        assertEquals(expectedDates, dates);
    }

    @Test
    void testGetAllBookingDateByVehicleId_ThrowsException_WhenTypeIsNotUser() {
        long vehicleId = 1L;

        assertThrows(RuntimeException.class, () -> bookingService.getAllBookingDateByVehicleId(vehicleId, "Admin"));
    }
}