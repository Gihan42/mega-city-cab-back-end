package com.mega.city.cab.backend.util.response;

import com.mega.city.cab.backend.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StripeResponse {
    String status;
    String message;
    String sessionId;
    String sessionUrl;
    Payment payment;
}
