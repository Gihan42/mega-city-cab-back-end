package com.mega.city.cab.backend.controller;

import com.mega.city.cab.backend.dto.BookingDto;
import com.mega.city.cab.backend.dto.CommentsDto;
import com.mega.city.cab.backend.entity.Booking;
import com.mega.city.cab.backend.service.BookingService;
import com.mega.city.cab.backend.util.response.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class BookingController {

    @Autowired
    BookingService bookingService;

//    save booking
    @PostMapping(path = "/save")
    public ResponseEntity<StandardResponse> saveBooking(@RequestBody BookingDto dto,
                                                        @RequestAttribute String type){
        Booking booking = bookingService.saveBooking(dto, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"Booking saved",booking),
                HttpStatus.OK
        );
    }

//    update booking status
    @PutMapping(params = {"bookingId"})
    public ResponseEntity<StandardResponse> updateBookingStatus(@RequestParam Long bookingId,
                                                                @RequestAttribute String type){
        Booking booking = bookingService.updateBookingStatus(bookingId, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"Booking Status Updated",booking),
                HttpStatus.OK
        );
    }

}
