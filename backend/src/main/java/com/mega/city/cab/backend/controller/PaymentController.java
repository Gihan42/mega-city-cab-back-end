package com.mega.city.cab.backend.controller;


import com.mega.city.cab.backend.dto.DriverDto;
import com.mega.city.cab.backend.dto.PaymentDto;
import com.mega.city.cab.backend.entity.Driver;
import com.mega.city.cab.backend.entity.custom.CustomPaymentDateResult;
import com.mega.city.cab.backend.entity.custom.CustomPaymentDetails;
import com.mega.city.cab.backend.entity.custom.CustomPaymentMonthResult;
import com.mega.city.cab.backend.entity.custom.CustomPaymentResult;
import com.mega.city.cab.backend.service.PaymentService;
import com.mega.city.cab.backend.util.response.StandardResponse;
import com.mega.city.cab.backend.util.response.StripeResponse;
import com.stripe.Stripe;
import com.stripe.model.billingportal.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

//    save payment
    @PostMapping(path = "/save")
    public ResponseEntity<StandardResponse> createPayment(@RequestBody PaymentDto dto,
                                                       @RequestAttribute String type) {
        StripeResponse payment = paymentService.createPayment(dto, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"Payment is Successfully ",payment),
                HttpStatus.OK
        );
    }

//    get all payments with customer,driver and vehivle
    @GetMapping(path = "/allPayments")
    public ResponseEntity<StandardResponse> getPayments ( @RequestAttribute String type) {
        List<CustomPaymentResult> allPayments = paymentService.getAllPayments(type);
        return new ResponseEntity<>(
                new StandardResponse(200,"all payments",allPayments),
                HttpStatus.OK
        );
    }

//    get total payment by total amount in this week day
    @GetMapping(path = "/getPaymentByThisWeekDay")
    public ResponseEntity<StandardResponse> getPaymentByThisWeekDay ( @RequestAttribute String type) {
        List<CustomPaymentDateResult> paymentByThisWeekDay = paymentService.getPaymentByThisWeekDay(type);
        return new ResponseEntity<>(
                new StandardResponse(200,"this week all payments",paymentByThisWeekDay),
                HttpStatus.OK
        );
    }

//    monthly get total payment
    @GetMapping(path = "/totalPaymentinThisMonth")
    public ResponseEntity<StandardResponse> getPaymentByThisMonth ( @RequestAttribute String type) {
        List<CustomPaymentMonthResult> paymentByThisMonth = paymentService.getPaymentByThisMonth(type);
        return new ResponseEntity<>(
                new StandardResponse(200,"monthly payments",paymentByThisMonth),
                HttpStatus.OK
        );
    }

//    export report
    @GetMapping(params = {"paymentId","format"})
    public ResponseEntity<byte[]> exportReport (@RequestParam long paymentId,
                                                          @RequestParam String format,
                                                          @RequestAttribute String type) {
        byte[] data = paymentService.returnExportReport(paymentId, format, type);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=report.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        return ResponseEntity.ok()
                .headers(headers)
                .body(data);

    }

//get payment status by paymentId
        @GetMapping(params = {"pId"})
        public ResponseEntity<StandardResponse> getPaymentStatusById(@RequestParam long pId,
                                                                     @RequestAttribute String type){
            String paymentStatusById = paymentService.getPaymentStatusById(pId, type);
            return new ResponseEntity<>(
                    new StandardResponse(200,"payment status",paymentStatusById),
                    HttpStatus.OK
            );

        }


}
