package com.mega.city.cab.backend.controller;


import com.mega.city.cab.backend.dto.DriverDto;
import com.mega.city.cab.backend.dto.PaymentDto;
import com.mega.city.cab.backend.entity.Driver;
import com.mega.city.cab.backend.service.PaymentService;
import com.mega.city.cab.backend.util.response.StandardResponse;
import com.mega.city.cab.backend.util.response.StripeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

//    save payment
    @PostMapping(path = "/save")
    public ResponseEntity<StandardResponse> saveDriver(@RequestBody PaymentDto dto,
                                                       @RequestAttribute String type) {
        StripeResponse payment = paymentService.createPayment(dto, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"Payment is Successfully ",payment),
                HttpStatus.OK
        );
    }
}
