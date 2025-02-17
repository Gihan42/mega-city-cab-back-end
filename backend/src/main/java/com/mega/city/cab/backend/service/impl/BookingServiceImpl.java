package com.mega.city.cab.backend.service.impl;

import com.mega.city.cab.backend.dto.BookingDto;
import com.mega.city.cab.backend.entity.Booking;
import com.mega.city.cab.backend.entity.custom.CustomBookingDetails;
import com.mega.city.cab.backend.entity.custom.CustomBookingResult;
import com.mega.city.cab.backend.repo.BookingRepo;
import com.mega.city.cab.backend.service.BookingService;
import com.mega.city.cab.backend.service.DriverService;
import com.mega.city.cab.backend.service.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepo bookingRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    DriverService driverService;

    @Scheduled(cron = "0 * * * * ?")
    public void checkAndUpdateBookingStatus() {
        Date now = new Date();
//        System.out.println("Checking bookings at: " + now);
        List<Booking> pendingBookings = bookingRepo.findByStatus();
        for (Booking booking : pendingBookings) {
//            System.out.println("Processing booking: " + booking.getBookingId());
            if (booking.getBookingDateTime().before(now)) {
              //  System.out.println("Booking date is before now: " + booking.getBookingDateTime());
                Booking bookingById = bookingRepo.getBookingById(booking.getBookingId());
                if (bookingById.getStatus().equals("Booking")) {
                    bookingById.setStatus("Pending");
                    vehicleService.changeVehicleStatus(booking.getVehicleId());
                    driverService.changeStatusInDriver(booking.getDriverId());
                    bookingRepo.save(bookingById);
                  //  System.out.println("Booking status updated to Pending: " + bookingById.getBookingId());
                } else {
                   // System.out.println("Booking status is not Booking: " + bookingById.getStatus());
                    throw new RuntimeException("booking status not pending");
                }
            }
        }
    }



/*
    @Override
    public Booking saveBooking(BookingDto booking, String type) {
        if (!type.equals("User")){
            throw new RuntimeException("dont have permission");
        }
        List<Date> allBookingDatesByVehicleId =
                bookingRepo.getAllBookingDatesByVehicleId(booking.getVehicleId());
        for (Date date : allBookingDatesByVehicleId) {
            if (booking.getBookingDateTime().equals(date)) {
                throw new RuntimeException("cannot booking this day");
            }
        }
        Booking map = modelMapper.map(booking, Booking.class);
        map.setStatus("Booking");
        System.out.println("map-"+map);
        return  bookingRepo.save(map);
    }
*/

    @Override
    public Booking saveBooking(BookingDto booking, String type) {
        if (!type.equals("User")) {
            throw new RuntimeException("dont have permission");
        }

        List<Date> allBookingDatesByVehicleId =
                bookingRepo.getAllBookingDatesByVehicleId(booking.getVehicleId());
        Date bookingDate = booking.getBookingDateTime();
        double hoursToAdd = Double.parseDouble(booking.getHours());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(bookingDate);


        int hours = (int) hoursToAdd;
        int minutes = (int) ((hoursToAdd - hours) * 60);


        calendar.add(Calendar.HOUR, hours);
        calendar.add(Calendar.MINUTE, minutes);
        Date updateBookingdDate = calendar.getTime();
//        System.out.println("Updated Booking Date: " + updateBookingdDate);
        for (Date existingDate : allBookingDatesByVehicleId) {

            if (bookingDate.equals(existingDate) || updateBookingdDate.equals(existingDate)) {
                throw new RuntimeException("Cannot book on this day. Date already booked.");
            }
            if (updateBookingdDate.after(existingDate)) {
                throw new RuntimeException("Cannot book. Overlapping with existing booking.");
            }
        }
        Booking map = modelMapper.map(booking, Booking.class);
        map.setStatus("Booking");
        return bookingRepo.save(map);
    }

    @Override
    public Booking updateBookingStatus(long bookingId, String type) {
        if (!type.equals("Admin")){
            throw new RuntimeException("dont have permission");
        }
        Booking bookingById = bookingRepo.getBookingById(bookingId);
        if(!Objects.equals(bookingById,null) && bookingById.getStatus().equals("Pending")){
            boolean updatedVehicleStatus = vehicleService.updateVehicleStatus(bookingById.getVehicleId());
            boolean updatedDriverStatus = driverService.updateStatusInDriver(bookingById.getDriverId());
            if(updatedVehicleStatus && updatedDriverStatus){
                bookingById.setStatus("Confirmed");
                return bookingRepo.save(bookingById);
            }
            throw new RuntimeException("vehicle or diver not changed");
        }
        throw new RuntimeException("booking not exist");
    }

    @Override
    public List<CustomBookingResult> getAllBookingByCustomer(long userId, String type) {
        if (!type.equals("User")){
            throw new RuntimeException("dont have permission");
        }
       return bookingRepo.getBookingByCustomerId(userId);
    }

    @Override
    public List<CustomBookingDetails> getBookingDetails(String type) {
        if (!type.equals("Admin")){
            throw new RuntimeException("dont have permission");
        }
        return bookingRepo.getBookingDetails();
    }

    @Override
    public int getPendingCount(String type) {
        if (!type.equals("Admin")){
            throw new RuntimeException("dont have permission");
        }
        return bookingRepo.getPendingCount();
    }

    @Override
    public List<Date> getAllBookingDateByVehicleId(long vehicleId,String type) {
        if (!type.equals("User")){
            throw new RuntimeException("dont have permission");
        }
        return  bookingRepo.getAllBookingDatesByVehicleId(vehicleId);
    }
}
