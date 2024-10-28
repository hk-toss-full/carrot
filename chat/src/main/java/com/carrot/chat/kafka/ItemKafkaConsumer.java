package com.carrot.chat.kafka;

import com.carrot.chat.domain.dto.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemKafkaConsumer {

    private final ItemServiceClient itemServiceClient;

    @KafkaListener(topics = "itemTopic", groupId = "itemGroup")
    public void listen(ItemResponse itemResponse) {
        Long itemId = itemResponse.itemId();
        itemServiceClient.getItemById(itemId);
    }
}
