package com.mega.city.cab.backend.repo;

import com.mega.city.cab.backend.entity.Payment;
import com.mega.city.cab.backend.entity.custom.CustomPaymentDateResult;
import com.mega.city.cab.backend.entity.custom.CustomPaymentMonthResult;
import com.mega.city.cab.backend.entity.custom.CustomPaymentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

}
