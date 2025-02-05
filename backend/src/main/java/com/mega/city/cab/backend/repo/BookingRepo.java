package com.mega.city.cab.backend.repo;

import com.mega.city.cab.backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepo extends JpaRepository<Booking,Long> {

    @Query(value = "select * from booking where booking_id = :bookingId",nativeQuery = true)
    Booking getBookingById(@Param("bookingId") long bookingId);
}
