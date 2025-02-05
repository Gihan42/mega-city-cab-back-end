package com.mega.city.cab.backend.entity.custom;

public interface CustomPaymentResult {
    Long getPaymentId();
    Long getBookingId();
    Double getAmount();
    String getPaymentMethod();
    Long getCustomerId();
    String getCustomerName();
    Long getVehicleId();
    String getVehicleModel();
    Long getDriverId();
    String getDriverName();
}
