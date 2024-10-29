package com.carrot.payment.dto;

import com.carrot.payment.domain.Payment;
import com.carrot.payment.domain.PaymentStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private Long userId;
    private Double amount;
    private PaymentStatus status;
    private String transactionId;

    public Payment toEntity() {
        return Payment.builder()
                .chatRoomId(1L)
                .userId(1L)
                .amount(this.amount)
                .status(this.status)
                .transactionId(transactionId)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
