package com.carrot.payment.dto;

import com.carrot.payment.domain.Payment;
import com.carrot.payment.domain.PaymentStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {
    private Long id;
    private Long userId;
    private Double amount;
    private PaymentStatus status;
    private String transactionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PaymentResponse fromEntity(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .userId(payment.getUserId())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .transactionId(payment.getTransactionId())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}
