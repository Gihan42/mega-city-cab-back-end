package com.mega.city.cab.backend.service.impl;

import com.mega.city.cab.backend.dto.BookingDto;
import com.mega.city.cab.backend.entity.Booking;
import com.mega.city.cab.backend.entity.Payment;
import com.mega.city.cab.backend.entity.custom.CustomBookingDetails;
import com.mega.city.cab.backend.entity.custom.CustomBookingResult;
import com.mega.city.cab.backend.entity.custom.CustomerBookingDate;
import com.mega.city.cab.backend.repo.BookingRepo;
import com.mega.city.cab.backend.repo.PaymentRepo;
import com.mega.city.cab.backend.service.BookingService;
import com.mega.city.cab.backend.service.DriverService;
import com.mega.city.cab.backend.service.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    @Autowired
    PaymentRepo paymentRepo;

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

/*    @Override
    public Booking saveBooking(BookingDto booking, String type) {
        if (!type.equals("User")){
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
        Date updatedBookingDate = calendar.getTime();
        for (Date date : allBookingDatesByVehicleId) {
            if (booking.getBookingDateTime().equals(date)) {
                throw new RuntimeException("cannot booking this day");
            }
        }
        Booking map = modelMapper.map(booking, Booking.class);
        map.setStatus("Booking");
        map.setEstimatedBookingDateTime(updatedBookingDate);
        return  bookingRepo.save(map);
    }*/

/*    @Override
    public Booking saveBooking(BookingDto booking, String type) {
        if (!type.equals("User")) {
            throw new RuntimeException("Don't have permission");
        }

        List<Date> allBookingDatesByVehicleId =
                bookingRepo.getAllBookingDatesByVehicleId(booking.getVehicleId());

        List<Date> allEstimatedBookingDateTimeByVehicleId =
                bookingRepo.getAllEstimatedBookingDateTimeByVehicleId(booking.getVehicleId());

        Date bookingDate = booking.getBookingDateTime();
        double hoursToAdd = Double.parseDouble(booking.getHours());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(bookingDate);

        int hours = (int) hoursToAdd;
        int minutes = (int) ((hoursToAdd - hours) * 60);

        calendar.add(Calendar.HOUR, hours);
        calendar.add(Calendar.MINUTE, minutes);
        Date updatedBookingDate = calendar.getTime();

        System.out.println("updatedBookingDate-" + updatedBookingDate);

        for (int i = 0; i < allBookingDatesByVehicleId.size(); i++) {
            Date existingDate = allBookingDatesByVehicleId.get(i);
            Date estimatedTime = allEstimatedBookingDateTimeByVehicleId.get(i);

            System.out.println("existingDate-" + existingDate);
            System.out.println("estimatedTime-" + estimatedTime);

            // 🚨 Prevent booking before an existing booking start date
            if (!bookingDate.before(existingDate) && updatedBookingDate.before(existingDate)) {
                throw new RuntimeException("Cannot book before an existing booking date.");
            }

            // 🚨 Prevent booking that starts within an existing booking period
            if (bookingDate.after(existingDate) && bookingDate.before(estimatedTime)) {
                throw new RuntimeException("Cannot book. The booking date falls within an existing booking period.");
            }

            // 🚨 Prevent overlapping updated booking date with existing bookings
            if (updatedBookingDate.after(existingDate) && updatedBookingDate.before(estimatedTime)) {
                throw new RuntimeException("Cannot book. The updated booking date overlaps with an existing booking.");
            }

            // 🚨 Prevent booking exactly on an existing booking date
            if (bookingDate.equals(existingDate) || updatedBookingDate.equals(existingDate)) {
                throw new RuntimeException("Cannot book on this day. Date already booked.");
            }
        }

        Booking map = modelMapper.map(booking, Booking.class);
        map.setStatus("Booking");
        map.setEstimatedBookingDateTime(updatedBookingDate);
        return bookingRepo.save(map);
    }*/


    @Override
    public Booking saveBooking(BookingDto booking, String type) {
        if (!type.equals("User")) {
            throw new RuntimeException("Don't have permission");
        }

        List<Date> allBookingDatesByVehicleId = bookingRepo.getAllBookingDatesByVehicleId(booking.getVehicleId());
        List<Booking> allBookingByVehicleId = bookingRepo.getAllBookingByVehicleId(booking.getVehicleId());

        Date bookingDate = booking.getBookingDateTime();
        double hoursToAdd = Double.parseDouble(booking.getHours());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(bookingDate);

        int hours = (int) hoursToAdd;
        int minutes = (int) ((hoursToAdd - hours) * 60);

        calendar.add(Calendar.HOUR, hours);
        calendar.add(Calendar.MINUTE, minutes);
        Date updatedBookingDate = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Format the current booking date to compare only the date part
        String nowBookingDate = sdf.format(bookingDate);

        for (Booking bookings : allBookingByVehicleId) {
            // Format the existing booking date to compare only the date part
            String formattedDate = sdf.format(bookings.getBookingDateTime());

//            System.out.println("Formatted Date: " + formattedDate);
//            System.out.println("nowBookingDate Date: " + nowBookingDate);

            // Check if the dates are the same (ignoring time)
            if (formattedDate.equals(nowBookingDate) &&
                    bookings.getStatus().equals("Booking") ||
                    bookings.getStatus().equals("Pending") || bookings.getStatus().equals("Booking not close")){
                throw new RuntimeException("Cannot book the vehicle on the same day. The date is already booked.");
            }

        }

        // If the date is not already booked, proceed to save the booking
        Booking map = modelMapper.map(booking, Booking.class);
        map.setStatus("Booking not close");
        map.setEstimatedBookingDateTime(updatedBookingDate);
        return bookingRepo.save(map);
    }

    @Override
    public Booking updateBookingStatus(long bookingId, String type) {
        if (!type.equals("Admin")) {
            throw new RuntimeException("Don't have permission");
        }

        System.out.println(bookingId);
        Booking bookingById = bookingRepo.getBookingById(bookingId);

        if (bookingById == null) {
            throw new RuntimeException("Booking not found");
        }

        System.out.println(bookingById.getStatus());

        if (bookingById.getStatus().equals("Pending")) {
            boolean updatedVehicleStatus = vehicleService.updateVehicleStatus(bookingById.getVehicleId());
            boolean updatedDriverStatus = driverService.updateStatusInDriver(bookingById.getDriverId());

            if (updatedVehicleStatus && updatedDriverStatus) {
                bookingById.setStatus("Confirmed");
                return bookingRepo.save(bookingById);
            }
            throw new RuntimeException("Vehicle or driver status not updated");
        }
        else if (bookingById.getStatus().equals("Booking not close")) {
            return deleteBooking(bookingId);
        }

        throw new RuntimeException("Invalid booking status");
    }

    private Booking deleteBooking(long bookingId) {
        System.out.println("Deleting booking...");

        Booking bookingById = bookingRepo.getBookingById(bookingId);
        Payment allPaymentByBookingId = paymentRepo.getAllPaymentByBookingId(bookingId);

        if (bookingById == null) {
            throw new RuntimeException("Booking not found");
        }

        if (allPaymentByBookingId != null) {
            paymentRepo.delete(allPaymentByBookingId);
        }

        bookingRepo.delete(bookingById);
        return bookingById;
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

    @Override
    public List<CustomerBookingDate> getAllBookingDatesAndEstimatedDateByVehicleId(long vehicleId, String type) {
        if (!type.equals("User")){
            throw new RuntimeException("dont have permission");
        }
        return bookingRepo.getAllBookingDatesAndEstimatedDateByVehicleId(vehicleId);
    }

    @Override
    public Booking updateStatusNotConfirmBookingWherePaymentId(long paymentId, String type) {
        if (!type.equals("User")){
            throw new RuntimeException("dont have permission");
        }
        Payment paymentById = paymentRepo.getPaymentById(paymentId);
        if(!Objects.equals(paymentById,null)){
            Booking bookingById = bookingRepo.getBookingById(paymentById.getBookingId());
            bookingById.setStatus("Booking");
            return bookingById;
        }
        throw new RuntimeException("payment not exist");
    }


}
