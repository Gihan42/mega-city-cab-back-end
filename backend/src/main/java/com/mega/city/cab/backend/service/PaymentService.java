package com.mega.city.cab.backend.service;

import com.mega.city.cab.backend.dto.PaymentDto;
import com.mega.city.cab.backend.entity.Payment;
import com.mega.city.cab.backend.util.response.StripeResponse;

public interface PaymentService {

    StripeResponse createPayment(PaymentDto paymentDto,String type);
    Payment savePayment(PaymentDto paymentDto);
}
