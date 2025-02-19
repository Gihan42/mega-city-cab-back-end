package com.mega.city.cab.backend.controller;

import com.mega.city.cab.backend.dto.BookingDto;
import com.mega.city.cab.backend.entity.Booking;
import com.mega.city.cab.backend.entity.custom.CustomBookingDetails;
import com.mega.city.cab.backend.entity.custom.CustomBookingResult;
import com.mega.city.cab.backend.entity.custom.CustomerBookingDate;
import com.mega.city.cab.backend.service.BookingService;
import com.mega.city.cab.backend.util.response.StandardResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBooking() {
        BookingDto dto = new BookingDto();
        String type = "user";
        Booking booking = new Booking();
        when(bookingService.saveBooking(dto, type)).thenReturn(booking);

        ResponseEntity<StandardResponse> response = bookingController.saveBooking(dto, type);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Booking saved", response.getBody().getMessage());
        assertEquals(booking, response.getBody().getData());
        verify(bookingService, times(1)).saveBooking(dto, type);
    }

    @Test
    void testUpdateBookingStatus() {
        long bookingId = 1L;
        String type = "admin";
        Booking booking = new Booking();
        when(bookingService.updateBookingStatus(bookingId, type)).thenReturn(booking);

        ResponseEntity<StandardResponse> response = bookingController.updateBookingStatus(bookingId, type);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Booking Status Updated", response.getBody().getMessage());
        assertEquals(booking, response.getBody().getData());
        verify(bookingService, times(1)).updateBookingStatus(bookingId, type);
    }

    @Test
    void testGetAllBookingsById() {
        long userId = 1L;
        String type = "user";
        CustomBookingResult mockBookingResult1 = mock(CustomBookingResult.class);
        CustomBookingResult mockBookingResult2 = mock(CustomBookingResult.class);
        List<CustomBookingResult> bookings = Arrays.asList(mockBookingResult1, mockBookingResult2);
        when(bookingService.getAllBookingByCustomer(userId, type)).thenReturn(bookings);

        ResponseEntity<StandardResponse> response = bookingController.getAllBookingsById(userId, type);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("get all bookings", response.getBody().getMessage());
        assertEquals(bookings, response.getBody().getData());
        verify(bookingService, times(1)).getAllBookingByCustomer(userId, type);
    }

    @Test
    void testGetAllBookingDetails() {
        String type = "admin";
        CustomBookingDetails mockBookingDetails1 = mock(CustomBookingDetails.class);
        CustomBookingDetails mockBookingDetails2 = mock(CustomBookingDetails.class);
        List<CustomBookingDetails> bookingDetails = Arrays.asList(mockBookingDetails1, mockBookingDetails2);
        when(bookingService.getBookingDetails(type)).thenReturn(bookingDetails);

        ResponseEntity<StandardResponse> response = bookingController.getAllBookingDetails(type);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("get all bookings details", response.getBody().getMessage());
        assertEquals(bookingDetails, response.getBody().getData());
        verify(bookingService, times(1)).getBookingDetails(type);
    }

    @Test
    void testGetPendingCount() {
        String type = "admin";
        int pendingCount = 5;
        when(bookingService.getPendingCount(type)).thenReturn(pendingCount);

        ResponseEntity<StandardResponse> response = bookingController.getPendingCount(type);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("get all  pending bookings count", response.getBody().getMessage());
        assertEquals(pendingCount, response.getBody().getData());
        verify(bookingService, times(1)).getPendingCount(type);
    }

    @Test
    void testGetAllBookingDatesAndEstimatedDateByVehicleId() {
        long vehicleId = 1L;
        String type = "admin";
        CustomerBookingDate mockBookingDate1 = mock(CustomerBookingDate.class);
        CustomerBookingDate mockBookingDate2 = mock(CustomerBookingDate.class);
        List<CustomerBookingDate> bookingDates = Arrays.asList(mockBookingDate1, mockBookingDate2);
        when(bookingService.getAllBookingDatesAndEstimatedDateByVehicleId(vehicleId, type)).thenReturn(bookingDates);

        ResponseEntity<StandardResponse> response = bookingController.getAllBookingDatesAndEstimatedDateByVehicleId(vehicleId, type);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("get all booking date and estimated date by vehicle Id", response.getBody().getMessage());
        assertEquals(bookingDates, response.getBody().getData());
        verify(bookingService, times(1)).getAllBookingDatesAndEstimatedDateByVehicleId(vehicleId, type);
    }
}
