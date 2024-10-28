package com.carrot.user.kafka;

import com.carrot.user.domain.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserKafkaProducer {

    private final KafkaTemplate<String, UserResponse> kafkaTemplate;

    public void sendUserUpdate(UserResponse userResponse) {
        kafkaTemplate.send("userTopic", userResponse);
    }

}