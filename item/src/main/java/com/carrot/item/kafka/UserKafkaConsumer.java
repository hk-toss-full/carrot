package com.carrot.item.kafka;

import com.carrot.item.domain.dto.UserResponse;
import com.carrot.item.domain.entity.Item;
import com.carrot.item.domain.entity.Like;
import com.carrot.item.repository.ItemRepository;
import com.carrot.item.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserKafkaConsumer {

    private final ItemRepository itemRepository;
    private final LikeRepository likeRepository;

    @KafkaListener(topics = "userTopic", groupId = "userGroup")
    public void listen(UserResponse userResponse) {
        Long userId = userResponse.userId();
        Item item = Item.builder()
                .userId(userId)
                .build();
        Item savedItem = itemRepository.save(item);

        Like like = Like.builder()
                .userId(userId)
                .item(savedItem)
                .build();

        likeRepository.save(like);
    }
}
