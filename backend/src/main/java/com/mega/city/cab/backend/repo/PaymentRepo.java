package com.mega.city.cab.backend.repo;

import com.mega.city.cab.backend.entity.Payment;
import com.mega.city.cab.backend.entity.custom.CustomPaymentDateResult;
import com.mega.city.cab.backend.entity.custom.CustomPaymentDetails;
import com.mega.city.cab.backend.entity.custom.CustomPaymentMonthResult;
import com.mega.city.cab.backend.entity.custom.CustomPaymentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<Payment,Long> {

    @Query(value = "select p.payment_id as paymentId,p.booking_id as bookingId,p.amount as amount,p.date as date,p.payment_method as paymentMethod,p.customer_id as customerId,u.username AS customerName,p.vehicle_id as vehicleId,v.vehicle_model as vehicleModel,d.driver_id as driverId,d.name AS driverName from payment p join user u on p.customer_id = u.user_id join vehicle v on p.vehicle_id = v.vehicle_id join  driver d on v.vehicle_id = d.driver_id order by p.payment_id desc ",nativeQuery = true)
    List<CustomPaymentResult> getPayments();

    @Query(value = "select DATE(date) as paymentDate,SUM(amount) as totalAmount from payment where date >= NOW() - interval 7 day and date < NOW() group  by DATE(date) order by paymentDate",nativeQuery = true)
    List<CustomPaymentDateResult> getPaymentByThisWeekDay();

    @Query(value = "\n" +
            "select\n" +
            "    DATE_FORMAT(date, '%M') as monthName,  -- Get full month name (e.g., January, February)\n" +
            "    SUM(amount) AS totalAmount\n" +
            "from payment\n" +
            "where YEAR(date) = YEAR(CURDATE())  -- Filters payments for the current year\n" +
            "group by MONTH(date), DATE_FORMAT(date, '%M')\n" +
            "order by  MONTH(date) asc",nativeQuery = true)
    List<CustomPaymentMonthResult> getPaymentByThisMonth();

    @Query(value = "SELECT\n" +
            "    p.payment_id as paymentId,\n" +
            "    b.booking_id as bookingId,\n" +
            "    u.user_id as userId,\n" +
            "    u.username as userName,\n" +
            "    b.pick_up_location as start_location,\n" +
            "    b.drop_location as drop_location,\n" +
            "    d.driver_id as driverId,\n" +
            "    d.name AS driver_name,\n" +
            "    v.plate_number AS vehicle_plate_number,\n" +
            "    v.vehicle_model AS vehicle_model,\n" +
            "    b.total_km as totalKm,\n" +
            "    b.hours as hours,\n" +
            "    p.currency as currency,\n" +
            "    p.amount as ammount,\n" +
            "    p.date as paymentDate\n" +
            "FROM payment p\n" +
            "         JOIN booking b ON p.booking_id = b.booking_id\n" +
            "         JOIN user u ON b.cutomer_id = u.user_id\n" +
            "         JOIN vehicle v ON b.vehicle_id = v.vehicle_id\n" +
            "         JOIN driver d ON v.vehicle_id = d.driver_id\n" +
            "WHERE p.payment_id = :paymentId",nativeQuery = true)
    CustomPaymentDetails getPaymentDetailsByPaymentId(@Param("paymentId") long paymentId);

}
