package com.mega.city.cab.backend.service;

import com.mega.city.cab.backend.dto.BookingDto;
import com.mega.city.cab.backend.entity.Booking;
import com.mega.city.cab.backend.entity.custom.CustomBookingDetails;
import com.mega.city.cab.backend.entity.custom.CustomBookingResult;
import com.mega.city.cab.backend.entity.custom.CustomerBookingDate;
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

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private BookingRepo bookingRepo;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private VehicleService vehicleService;

    @Mock
    private DriverService driverService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBooking_Success() {
        // Arrange
        BookingDto bookingDto = new BookingDto();
        bookingDto.setVehicleId(1L);
        bookingDto.setBookingDateTime(new Date());
        bookingDto.setHours("2.5");

        Booking booking = new Booking();
        booking.setBookingId(1L);
        booking.setStatus("Booking");

        when(modelMapper.map(bookingDto, Booking.class)).thenReturn(booking);
        when(bookingRepo.getAllBookingByVehicleId(1L)).thenReturn(Collections.emptyList());
        when(bookingRepo.save(booking)).thenReturn(booking);

        // Act
        Booking result = bookingService.saveBooking(bookingDto, "User");

        // Assert
        assertNotNull(result);
        assertEquals("Booking", result.getStatus());
        verify(bookingRepo, times(1)).save(booking);
    }

    @Test
    void testSaveBooking_ThrowsException_WhenNotUser() {
        // Arrange
        BookingDto bookingDto = new BookingDto();

        // Act & Assert
        assertThrows(RuntimeException.class, () -> bookingService.saveBooking(bookingDto, "Admin"));
    }

    @Test
    void testSaveBooking_ThrowsException_WhenDateAlreadyBooked() {
        // Arrange
        BookingDto bookingDto = new BookingDto();
        bookingDto.setVehicleId(1L);
        bookingDto.setBookingDateTime(new Date());
        bookingDto.setHours("2.5");

        Booking existingBooking = new Booking();
        existingBooking.setBookingDateTime(new Date());
        existingBooking.setStatus("Booking");

        when(bookingRepo.getAllBookingByVehicleId(1L)).thenReturn(Collections.singletonList(existingBooking));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> bookingService.saveBooking(bookingDto, "User"));
    }

    @Test
    void testUpdateBookingStatus_Success() {
        // Arrange
        long bookingId = 1L;
        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setStatus("Pending");
        booking.setVehicleId(1L);
        booking.setDriverId(1L);

        when(bookingRepo.getBookingById(bookingId)).thenReturn(booking);
        when(vehicleService.updateVehicleStatus(1L)).thenReturn(true);
        when(driverService.updateStatusInDriver(1L)).thenReturn(true);
        when(bookingRepo.save(booking)).thenReturn(booking);

        // Act
        Booking result = bookingService.updateBookingStatus(bookingId, "Admin");

        // Assert
        assertNotNull(result);
        assertEquals("Confirmed", result.getStatus());
        verify(bookingRepo, times(1)).save(booking);
    }

    @Test
    void testUpdateBookingStatus_ThrowsException_WhenNotAdmin() {
        // Arrange
        long bookingId = 1L;

        // Act & Assert
        assertThrows(RuntimeException.class, () -> bookingService.updateBookingStatus(bookingId, "User"));
    }

    @Test
    void testUpdateBookingStatus_ThrowsException_WhenBookingNotExist() {
        // Arrange
        long bookingId = 1L;

        when(bookingRepo.getBookingById(bookingId)).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> bookingService.updateBookingStatus(bookingId, "Admin"));
    }

    @Test
    void testGetAllBookingByCustomer_Success() {
        // Arrange
        long userId = 1L;
        when(bookingRepo.getBookingByCustomerId(userId)).thenReturn(Collections.emptyList());

        // Act
        List<CustomBookingResult> result = bookingService.getAllBookingByCustomer(userId, "User");

        // Assert
        assertNotNull(result);
        verify(bookingRepo, times(1)).getBookingByCustomerId(userId);
    }

    @Test
    void testGetAllBookingByCustomer_ThrowsException_WhenNotUser() {
        // Arrange
        long userId = 1L;

        // Act & Assert
        assertThrows(RuntimeException.class, () -> bookingService.getAllBookingByCustomer(userId, "Admin"));
    }

    @Test
    void testGetBookingDetails_Success() {
        // Arrange
        when(bookingRepo.getBookingDetails()).thenReturn(Collections.emptyList());

        // Act
        List<CustomBookingDetails> result = bookingService.getBookingDetails("Admin");

        // Assert
        assertNotNull(result);
        verify(bookingRepo, times(1)).getBookingDetails();
    }

    @Test
    void testGetBookingDetails_ThrowsException_WhenNotAdmin() {
        // Act & Assert
        assertThrows(RuntimeException.class, () -> bookingService.getBookingDetails("User"));
    }

    @Test
    void testGetPendingCount_Success() {
        // Arrange
        when(bookingRepo.getPendingCount()).thenReturn(5);

        // Act
        int result = bookingService.getPendingCount("Admin");

        // Assert
        assertEquals(5, result);
        verify(bookingRepo, times(1)).getPendingCount();
    }

    @Test
    void testGetPendingCount_ThrowsException_WhenNotAdmin() {
        // Act & Assert
        assertThrows(RuntimeException.class, () -> bookingService.getPendingCount("User"));
    }

    @Test
    void testGetAllBookingDateByVehicleId_Success() {
        // Arrange
        long vehicleId = 1L;
        when(bookingRepo.getAllBookingDatesByVehicleId(vehicleId)).thenReturn(Collections.emptyList());

        // Act
        List<Date> result = bookingService.getAllBookingDateByVehicleId(vehicleId, "User");

        // Assert
        assertNotNull(result);
        verify(bookingRepo, times(1)).getAllBookingDatesByVehicleId(vehicleId);
    }

    @Test
    void testGetAllBookingDateByVehicleId_ThrowsException_WhenNotUser() {
        // Arrange
        long vehicleId = 1L;

        // Act & Assert
        assertThrows(RuntimeException.class, () -> bookingService.getAllBookingDateByVehicleId(vehicleId, "Admin"));
    }

    @Test
    void testGetAllBookingDatesAndEstimatedDateByVehicleId_Success() {
        // Arrange
        long vehicleId = 1L;
        when(bookingRepo.getAllBookingDatesAndEstimatedDateByVehicleId(vehicleId)).thenReturn(Collections.emptyList());

        // Act
        List<CustomerBookingDate> result = bookingService.getAllBookingDatesAndEstimatedDateByVehicleId(vehicleId, "User");

        // Assert
        assertNotNull(result);
        verify(bookingRepo, times(1)).getAllBookingDatesAndEstimatedDateByVehicleId(vehicleId);
    }

    @Test
    void testGetAllBookingDatesAndEstimatedDateByVehicleId_ThrowsException_WhenNotUser() {
        // Arrange
        long vehicleId = 1L;

        // Act & Assert
        assertThrows(RuntimeException.class, () -> bookingService.getAllBookingDatesAndEstimatedDateByVehicleId(vehicleId, "Admin"));
    }
}