package com.mega.city.cab.backend.entity.custom;

import java.util.Date;

public interface CustomBookingResult {
    long getBookingId();
    String getStartLocation();
    String getDropLocation();
    long getDriverId();
    String getDriverName();
    String getVehiclePlateNumber();
    String getVehicleModel();
    Double getAmount();
    Date getBookingDate();
}
