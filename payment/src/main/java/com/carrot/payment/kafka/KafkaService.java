package com.carrot.payment.kafka;

public interface KafkaService {
    void sendPaymentEvent(KafkaMessage message);
}
