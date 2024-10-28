package com.carrot.chat.kafka;

import com.carrot.chat.domain.dto.UserResponse;
import com.carrot.chat.domain.entity.ChatMessage;
import com.carrot.chat.domain.entity.EnterChat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserKafkaConsumer {

    private final MongoTemplate mongoTemplate;

    @KafkaListener(topics = "userTopic", groupId = "userGroup")
    public void listen(UserResponse userResponse) {
        Long userId = userResponse.userId();
        ChatMessage chatMessage = ChatMessage.builder()
                .userId(userId)
                .build();
        EnterChat enterChat = EnterChat.builder()
                .userId(userId)
                .build();
        mongoTemplate.save(chatMessage, "chatMessages");
        mongoTemplate.save(enterChat, "enterChats");
    }
}
