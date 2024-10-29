package com.carrot.chat.kafka;

import com.carrot.chat.domain.dto.ItemResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "item-service")
public interface ItemServiceClient {

    @GetMapping("/api/users/{itemId}")
    ItemResponse getItemById(@PathVariable("itemId") Long itemId);
}
