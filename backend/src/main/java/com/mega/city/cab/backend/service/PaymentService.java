package com.mega.city.cab.backend.service;

import com.mega.city.cab.backend.dto.PaymentDto;
import com.mega.city.cab.backend.entity.Payment;
import com.mega.city.cab.backend.entity.custom.CustomPaymentDateResult;
import com.mega.city.cab.backend.entity.custom.CustomPaymentMonthResult;
import com.mega.city.cab.backend.entity.custom.CustomPaymentResult;
import com.mega.city.cab.backend.util.response.StripeResponse;

import java.util.List;

public interface PaymentService {

    StripeResponse createPayment(PaymentDto paymentDto,String type);
    Payment savePayment(PaymentDto paymentDto);
    List<CustomPaymentResult> getAllPayments(String type);
    List<CustomPaymentDateResult> getPaymentByThisWeekDay(String type);
    List<CustomPaymentMonthResult> getPaymentByThisMonth(String type);

}
