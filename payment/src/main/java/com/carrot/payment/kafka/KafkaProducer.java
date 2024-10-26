package com.carrot.payment.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer implements KafkaService {

    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;
    private static final String topic = "payment_topic";  // Topic 설정

    @Override
    public void sendPaymentEvent(KafkaMessage message) {
        kafkaTemplate.send(topic, message);
        System.out.println(message);
    }
}
