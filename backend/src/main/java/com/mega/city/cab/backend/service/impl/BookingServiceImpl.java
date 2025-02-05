package com.mega.city.cab.backend.service.impl;

import com.mega.city.cab.backend.dto.BookingDto;
import com.mega.city.cab.backend.entity.Booking;
import com.mega.city.cab.backend.entity.custom.CustomBookingResult;
import com.mega.city.cab.backend.repo.BookingRepo;
import com.mega.city.cab.backend.service.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepo bookingRepo;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public Booking saveBooking(BookingDto booking, String type) {
        if (!type.equals("User")){
            throw new RuntimeException("dont have permission");
        }
        Booking map = modelMapper.map(booking, Booking.class);
        map.setStatus("Pending");
        return  bookingRepo.save(map);

    }

    @Override
    public Booking updateBookingStatus(long bookingId, String type) {
        if (!type.equals("Admin")){
            throw new RuntimeException("dont have permission");
        }
        Booking bookingById = bookingRepo.getBookingById(bookingId);
        if(!Objects.equals(bookingById,null) && bookingById.getStatus().equals("Pending")){
            bookingById.setStatus("Confirmed");
            return bookingRepo.save(bookingById);
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
}
