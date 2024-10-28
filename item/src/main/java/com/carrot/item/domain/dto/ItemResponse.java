package com.carrot.item.domain.dto;

import com.carrot.item.domain.entity.Item;
import com.carrot.item.domain.entity.ItemStatus;

import java.util.Date;

public record ItemResponse(
        Long userId,
        String itemTitle,
        String itemContent,
        Date createdAt,
        Date updatedAt,
        ItemStatus status
) {

    public static ItemResponse from(Item item) {
        return new ItemResponse(
                item.getUserId(),
                item.getItemTitle(),
                item.getItemContent(),
                item.getCreatedAt(),
                item.getUpdatedAt(),
                item.getStatus()
        );
    }
}
