package com.mega.city.cab.backend.entity.custom;

import java.util.Date;

public interface CustomPaymentResult {
    Long getPaymentId();
    Long getBookingId();
    Double getAmount();
    Date getDate();
    String getPaymentMethod();
    Long getCustomerId();
    String getCustomerName();
    Long getVehicleId();
    String getVehicleModel();
    Long getDriverId();
    String getDriverName();
}
