package com.carrot.payment.util;

import com.carrot.payment.domain.Payment;
import com.carrot.payment.dto.PaymentRequest;
import com.carrot.payment.dto.PaymentResponse;
import java.time.LocalDateTime;

public class PaymentMapper {

    private PaymentMapper() {
        // Utility class should not be instantiated
    }

    public static Payment toEntity(PaymentRequest request) {
        return Payment.builder()
                .userId(request.getUserId())
                .price(request.getPrice())
                .status(request.getStatus())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static PaymentResponse toResponseDto(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .userId(payment.getUserId())
                .price(payment.getPrice())
                .status(payment.getStatus())
                .transactionId(payment.getTransactionId())
                .createdAt(LocalDateTime.parse(payment.getCreatedAt().toString()))
                .updatedAt(LocalDateTime.parse(payment.getUpdatedAt().toString()))
                .build();
    }
}
