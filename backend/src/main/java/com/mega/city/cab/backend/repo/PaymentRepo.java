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

    @Query(value = "SELECT\n" +
            "    p.payment_id AS paymentId,\n" +
            "    p.booking_id AS bookingId,\n" +
            "    p.amount AS amount,\n" +
            "    p.date AS date,\n" +
            "    p.payment_method AS paymentMethod,\n" +
            "    p.customer_id AS customerId,\n" +
            "    u.username AS customerName,\n" +
            "    p.vehicle_id AS vehicleId,\n" +
            "    v.vehicle_model AS vehicleModel,\n" +
            "    d.driver_id AS driverId,\n" +
            "    d.name AS driverName\n" +
            "FROM payment p\n" +
            "         JOIN user u ON p.customer_id = u.user_id\n" +
            "         JOIN vehicle v ON p.vehicle_id = v.vehicle_id\n" +
            "         JOIN driver d ON v.vehicle_id = d.driver_id\n" +
            "ORDER BY p.payment_id DESC\n",nativeQuery = true)
    List<CustomPaymentResult> getPayments();

    @Query(value = "SELECT\n" +
            "    DATE(date) AS paymentDate,\n" +
            "    SUM(amount) AS totalAmount\n" +
            "FROM\n" +
            "    payment\n" +
            "WHERE\n" +
            "    date >= CURDATE() - INTERVAL 7 DAY\n" +
            "GROUP BY\n" +
            "    DATE(date)\n" +
            "ORDER BY\n" +
            "    paymentDate DESC;",nativeQuery = true)
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
            "    b.driver_id as driverId,\n" +
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
            "         JOIN driver d ON b.driver_id = d.driver_id\n" +
            "WHERE p.payment_id = :paymentId",nativeQuery = true)
    CustomPaymentDetails getPaymentDetailsByPaymentId(@Param("paymentId") long paymentId);

    @Query(value = "select status from payment where payment_id =:paymentId",nativeQuery = true)
    String getPaymentStatusById(@Param("paymentId") long paymentId);

}
