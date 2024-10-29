package com.carrot.payment.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer implements KafkaService {

    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;
    private static final String TOPIC = "payment_topic";

    @Override
    public void sendPaymentEvent(KafkaMessage message) {
        kafkaTemplate.send(TOPIC, message);
        System.out.println("Sent Kafka message: " + message);
    }
}