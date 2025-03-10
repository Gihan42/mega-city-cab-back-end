package com.mega.city.cab.backend.controller;

import com.mega.city.cab.backend.dto.BookingDto;
import com.mega.city.cab.backend.dto.CommentsDto;
import com.mega.city.cab.backend.entity.Booking;
import com.mega.city.cab.backend.entity.custom.CustomBookingDetails;
import com.mega.city.cab.backend.entity.custom.CustomBookingResult;
import com.mega.city.cab.backend.entity.custom.CustomerBookingDate;
import com.mega.city.cab.backend.service.BookingService;
import com.mega.city.cab.backend.util.response.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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
    public ResponseEntity<StandardResponse> updateBookingStatus(@RequestParam long bookingId,
                                                                @RequestAttribute String type){
        Booking booking = bookingService.updateBookingStatus(bookingId, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"Booking Status Updated",booking),
                HttpStatus.OK
        );
    }

//    get booking details by user id
    @GetMapping(params = {"userId"})
    public ResponseEntity<StandardResponse> getAllBookingsById(@RequestParam long userId,
                                                                @RequestAttribute String type){
        List<CustomBookingResult> allBookingByCustomer = bookingService.getAllBookingByCustomer(userId, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"get all bookings",allBookingByCustomer),
                HttpStatus.OK
        );
    }

//    get booking details
    @GetMapping(path = "/bookingDetails")
    public ResponseEntity<StandardResponse> getAllBookingDetails( @RequestAttribute String type){
        List<CustomBookingDetails> bookingDetails = bookingService.getBookingDetails(type);
        return new ResponseEntity<>(
                new StandardResponse(200,"get all bookings details",bookingDetails),
                HttpStatus.OK
        );
    }

//    get Pending booking count
    @GetMapping(path = "/pending/count")
    public ResponseEntity<StandardResponse>getPendingCount(@RequestAttribute String type){
        int pendingCount = bookingService.getPendingCount(type);
        return new ResponseEntity<>(
                new StandardResponse(200,"get all  pending bookings count",pendingCount),
                HttpStatus.OK
        );

    }

//    get all booking dates by vehicle id
    @GetMapping(params = {"vehicleId"})
    public ResponseEntity<StandardResponse> getAllBookingDatesAndEstimatedDateByVehicleId(@RequestParam long vehicleId,@RequestAttribute String type){
        List<CustomerBookingDate> allBookingDatesAndEstimatedDateByVehicleId = bookingService.getAllBookingDatesAndEstimatedDateByVehicleId(vehicleId, type);

        return new ResponseEntity<>(
                new StandardResponse(200,"get all booking date and estimated date by vehicle Id",allBookingDatesAndEstimatedDateByVehicleId),
                HttpStatus.OK
        );
    }

    @PutMapping(params = {"pId"})
    public ResponseEntity<StandardResponse> updateStatusNotConfirmBookingWherePaymentId(@RequestParam long pId, @RequestAttribute String type){
        Booking booking = bookingService.updateStatusNotConfirmBookingWherePaymentId(pId, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"updated status",booking),
                HttpStatus.OK
        );
    }

}
