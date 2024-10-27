package com.carrot.payment.kafka;

import com.carrot.payment.domain.Payment;
import com.carrot.payment.domain.PaymentStatus;
import com.carrot.payment.dto.PaymentRequest;
import com.carrot.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final PaymentService service;

    @KafkaListener(topics = "payment_topic", groupId = "payment-consumer-group")
    public void processPaymentMessage(KafkaMessage message) {
        System.out.println("Received Kafka message: " + message);

        Payment payment = message.getPayment();

        switch (message.getAction()) {
            case "CREATED":
                // 새로운 결제 생성 로직
                service.savePaymentDetail(new PaymentRequest());
                break;
            case "APPROVED":
                // 결제 승인 로직
                service.updatePaymentStatus(payment.getId(), PaymentStatus.APPROVED);
                break;
            case "CANCELED":
                // 결제 취소 로직
                service.updatePaymentStatus(payment.getId(), PaymentStatus.CANCELED);
                break;
            default:
                throw new IllegalArgumentException("Unknown action: " + message.getAction());
        }
    }
}
