package com.carrot.chat.kafka;

import com.carrot.chat.domain.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserKafkaConsumer {

    private final UserServiceClient userServiceClient;

    @KafkaListener(topics = "userTopic", groupId = "userGroup")
    public void listen(UserResponse userResponse) {
        Long userId = userResponse.userId();
        userServiceClient.getUserById(userId);
    }
}
