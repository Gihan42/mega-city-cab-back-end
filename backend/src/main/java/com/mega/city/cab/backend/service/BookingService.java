package com.mega.city.cab.backend.service;

import com.mega.city.cab.backend.dto.BookingDto;
import com.mega.city.cab.backend.entity.Booking;
import com.mega.city.cab.backend.entity.custom.CustomBookingDetails;
import com.mega.city.cab.backend.entity.custom.CustomBookingResult;
import com.mega.city.cab.backend.entity.custom.CustomerBookingDate;

import java.util.Date;
import java.util.List;

public interface BookingService {
    Booking saveBooking(BookingDto booking, String type);
    Booking updateBookingStatus(long bookingId, String type);
    List<CustomBookingResult> getAllBookingByCustomer(long userId,String type);
    List<CustomBookingDetails> getBookingDetails(String type);
    int getPendingCount(String type);
    List<Date> getAllBookingDateByVehicleId(long vehicleId,String type);
    List<CustomerBookingDate> getAllBookingDatesAndEstimatedDateByVehicleId(long vehicleId,String type);
    Booking updateStatusNotConfirmBookingWherePaymentId(long paymentId,String type);


}
