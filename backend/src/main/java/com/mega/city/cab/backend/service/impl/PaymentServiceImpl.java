package com.mega.city.cab.backend.service.impl;

import com.mega.city.cab.backend.dto.PaymentDto;
import com.mega.city.cab.backend.entity.Payment;
import com.mega.city.cab.backend.repo.PaymentRepo;
import com.mega.city.cab.backend.service.PaymentService;
import com.mega.city.cab.backend.util.response.StripeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentRepo paymentRepo;

    @Autowired
    ModelMapper modelMapper;

    @Value("${stripe.Secretkey}")
    private String secretkey;


    @Override
    public StripeResponse createPayment(PaymentDto paymentDto, String type) {
        if (!type.equals("User")) {
            throw new BadCredentialsException("dont have permission");
        }

        Stripe.apiKey = secretkey;
        try {
            // Build Product Data
            SessionCreateParams.LineItem.PriceData.ProductData productData =
                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                            .setName(String.valueOf(paymentDto.getVehicleId()))
                            .build();

            // Build Price Data
            SessionCreateParams.LineItem.PriceData priceData =
                    SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("lkr")
                            .setUnitAmount((long) (paymentDto.getAmount() * 100))
                            .setProductData(productData)
                            .build();

            // Build Line Item
            SessionCreateParams.LineItem lineItem =
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(priceData)
                            .build();

            // Build Session Parameters
            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setSuccessUrl("http://localhost:8080/success")
                            .setCancelUrl("http://localhost:8080/cancel")
                            .addAllLineItem(Collections.singletonList(lineItem))
                            .build();

            Session session = Session.create(params);
            return new StripeResponse(
                    "SUCCESS",
                    "Payment session created successfully.",
                    session.getId(),
                    session.getUrl(),
                    savePayment(paymentDto)
            );



        } catch (StripeException e) {
            return new StripeResponse(
                    "ERROR",
                    "Failed to create payment session: " + e.getMessage(),
                    null,
                    null,
                    null
            );
        }
    }

    @Override
    public Payment savePayment(PaymentDto paymentDto) {
        Payment map = modelMapper.map(paymentDto, Payment.class);
        return paymentRepo.save(map);
    }


}
