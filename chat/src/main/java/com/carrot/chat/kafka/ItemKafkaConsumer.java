package com.carrot.chat.kafka;

import com.carrot.chat.domain.dto.ItemResponse;
import com.carrot.chat.domain.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemKafkaConsumer {

    private final MongoTemplate mongoTemplate;

    @KafkaListener(topics = "itemTopic", groupId = "itemGroup")
    public void listen(ItemResponse itemResponse) {
        Long itemId = itemResponse.itemId();
        ChatRoom chatRoom = ChatRoom.builder()
                .itemId(itemId)
                .build();
        mongoTemplate.save(chatRoom, "chatRooms");
    }
}
