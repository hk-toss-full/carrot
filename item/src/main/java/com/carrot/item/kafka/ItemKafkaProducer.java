package com.carrot.item.kafka;

import com.carrot.item.domain.dto.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemKafkaProducer {

    private final KafkaTemplate<String, ItemResponse> kafkaTemplate;

    public void sendUserUpdate(ItemResponse itemResponse) {
        kafkaTemplate.send("itemTopic", itemResponse);
    }
}
