package com.mega.city.cab.backend.entity.custom;

public interface VehicleCustomResult {
    long getVehicleId();
    String getModel();
    String getPlateNumber();
    String getCategory();
    double getPricePerKm();
    int getPassengerCount();
    String getImage();
}
