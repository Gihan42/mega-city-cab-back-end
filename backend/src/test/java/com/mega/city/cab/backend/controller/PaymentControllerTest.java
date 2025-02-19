package com.mega.city.cab.backend.controller;

import com.mega.city.cab.backend.dto.PaymentDto;
import com.mega.city.cab.backend.entity.custom.CustomPaymentDateResult;
import com.mega.city.cab.backend.entity.custom.CustomPaymentMonthResult;
import com.mega.city.cab.backend.entity.custom.CustomPaymentResult;
import com.mega.city.cab.backend.service.PaymentService;
import com.mega.city.cab.backend.util.response.StandardResponse;
import com.mega.city.cab.backend.util.response.StripeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

/*    @Test
    void testCreatePayment() {
        PaymentDto paymentDto = new PaymentDto();
        StripeResponse stripeResponse = new StripeResponse("status", "message", "paymentId", "clientSecret", null);
        when(paymentService.createPayment(paymentDto, "type")).thenReturn(stripeResponse);

        ResponseEntity<StandardResponse> response = paymentController.createPayment(paymentDto, "type");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Payment is Successfully", response.getBody().getMessage());
        assertEquals(stripeResponse, response.getBody().getData());
    }*/

    @Test
    void testGetPayments() {
        CustomPaymentResult paymentResult = mock(CustomPaymentResult.class);
        List<CustomPaymentResult> paymentResults = Arrays.asList(paymentResult);
        when(paymentService.getAllPayments("type")).thenReturn(paymentResults);

        ResponseEntity<StandardResponse> response = paymentController.getPayments("type");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("all payments", response.getBody().getMessage());
        assertEquals(paymentResults, response.getBody().getData());
    }

    @Test
    void testGetPaymentByThisWeekDay() {
        CustomPaymentDateResult paymentDateResult = mock(CustomPaymentDateResult.class);
        List<CustomPaymentDateResult> paymentDateResults = Arrays.asList(paymentDateResult);
        when(paymentService.getPaymentByThisWeekDay("type")).thenReturn(paymentDateResults);

        ResponseEntity<StandardResponse> response = paymentController.getPaymentByThisWeekDay("type");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("this week all payments", response.getBody().getMessage());
        assertEquals(paymentDateResults, response.getBody().getData());
    }

    @Test
    void testGetPaymentByThisMonth() {
        CustomPaymentMonthResult paymentMonthResult = mock(CustomPaymentMonthResult.class);
        List<CustomPaymentMonthResult> paymentMonthResults = Arrays.asList(paymentMonthResult);
        when(paymentService.getPaymentByThisMonth("type")).thenReturn(paymentMonthResults);

        ResponseEntity<StandardResponse> response = paymentController.getPaymentByThisMonth("type");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("monthly payments", response.getBody().getMessage());
        assertEquals(paymentMonthResults, response.getBody().getData());
    }

    @Test
    void testGetPaymentStatusById() {
        when(paymentService.getPaymentStatusById(1L, "type")).thenReturn("success");

        ResponseEntity<StandardResponse> response = paymentController.getPaymentStatusById(1L, "type");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("payment status", response.getBody().getMessage());
        assertEquals("success", response.getBody().getData());
    }
}