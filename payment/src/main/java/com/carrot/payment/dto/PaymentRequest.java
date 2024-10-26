package com.carrot.payment.dto;

import com.carrot.payment.domain.PaymentStatus;
import lombok.Data;

@Data
public class PaymentRequest {
    private Long userId;
    private Double price;
    private PaymentStatus status;
}
